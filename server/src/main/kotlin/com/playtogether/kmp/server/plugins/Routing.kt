package com.playtogether.kmp.server.plugins

import com.playtogether.kmp.data.models.Room
import com.playtogether.kmp.data.models.server.AuthResponse
import com.playtogether.kmp.data.models.server.MessageResponse
import com.playtogether.kmp.data.util.Constants
import com.playtogether.kmp.data.util.RegexPatterns
import com.playtogether.kmp.server.InvalidCredentialsException
import com.playtogether.kmp.server.InvalidEmailException
import com.playtogether.kmp.server.InvalidParameterException
import com.playtogether.kmp.server.InvalidPasswordException
import com.playtogether.kmp.server.PTException
import com.playtogether.kmp.server.fetchEmailFromToken
import com.playtogether.kmp.server.fetchParam
import com.playtogether.kmp.server.repositories.AuthRepository
import com.playtogether.kmp.server.repositories.RoomRepository
import com.playtogether.kmp.server.repositories.UserRepository
import com.playtogether.kmp.server.safeCall
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.auth.authenticate
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.request.receiveMultipart
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.uuid.UUID
import org.koin.ktor.ext.inject


fun Application.setupRouting() {
    install(ContentNegotiation) { json() }

    val authRepository by inject<AuthRepository>()
    val userRepository by inject<UserRepository>()
    val roomRepository by inject<RoomRepository>()
    val tokenService by inject<TokenService>()
    val tokenConfig by inject<TokenConfig>()
    val hashingService by inject<HashingService>()

    suspend fun ApplicationCall.respondJwt(
        email: String,
        password: String? = null,
        verifyPasswordValidity: Boolean = password != null
    ) {
        val user = userRepository.getUserByEmail(email)
        val isValidHashPassword = password?.let { nnPasswordParam ->
            hashingService.verify(
                value = nnPasswordParam,
                saltedHash = SaltedHash(
                    hash = user.hashedPassword
                        ?: throw PTException(Constants.Server.Exceptions.InvalidHashPassword),
                    salt = user.salt
                        ?: throw PTException(Constants.Server.Exceptions.InvalidSalt)
                )
            )
        }

        if (isValidHashPassword == true || !verifyPasswordValidity) {
            val jwt = tokenService.generateToken(
                config = tokenConfig,
                TokenClaim(
                    name = Constants.Server.JWTClaimEmail,
                    value = user.email
                )
            )

            authRepository.updateRefreshToken(
                email = email,
                previousExpiresAt = user.expiresAt
            )

            // Because the refresh token might be updated in the previous step
            val updatedUser = userRepository.getUserByEmail(user.email)

            respond(
                AuthResponse(
                    accessToken = jwt,
                    user = updatedUser.copy(
                        // Because the client doesn't need to see these values
                        salt = null,
                        hashedPassword = null
                    )
                )
            )
        } else throw InvalidCredentialsException
    }

    routing {
        post(Constants.Server.Routes.login) {
            safeCall {
                val emailParam = call.parameters[Constants.Server.Params.UserEmail]
                    ?: throw InvalidParameterException(Constants.Server.Params.UserEmail)

                val passwordParam = call.parameters[Constants.Server.Params.UserPassword]
                    ?: throw InvalidParameterException(Constants.Server.Params.UserPassword)

                call.respondJwt(
                    email = emailParam,
                    password = passwordParam
                )
            }
        }

        post(Constants.Server.Routes.register) {
            safeCall {
                val emailParam = call.parameters[Constants.Server.Params.UserEmail]
                    ?: throw InvalidParameterException(Constants.Server.Params.UserEmail)

                val passwordParam = call.parameters[Constants.Server.Params.UserPassword]
                    ?: throw InvalidParameterException(Constants.Server.Params.UserPassword)

                val saltedHash = hashingService.generateSaltedHash(value = passwordParam)

                val isValidEmail = RegexPatterns.Email.matches(emailParam)
                val isValidPassword = RegexPatterns.Password.matches(passwordParam)

                if (!isValidEmail) throw InvalidEmailException
                if (!isValidPassword) throw InvalidPasswordException

                authRepository.register(
                    email = emailParam,
                    hashedPassword = saltedHash.hash,
                    salt = saltedHash.salt
                )

                call.respondJwt(
                    email = emailParam,
                    password = passwordParam,
                )
            }
        }

        authenticate {
            patch(Constants.Server.Routes.updateUserProfile) {
                safeCall {
                    val multipartData = call.receiveMultipart()

                    val email = call.fetchEmailFromToken()

                    val wasUserUpdated = userRepository.updateUser(
                        email = email,
                        multipartData = multipartData
                    )

                    with(Constants.Server.ResponseMessages) {
                        call.respondText(
                            if (wasUserUpdated) UserUpdateSuccess else UserUpdateFailure
                        )
                    }
                }
            }

            delete(Constants.Server.Routes.deleteUserProfile) {
                safeCall {
                    val email = call.fetchEmailFromToken()
                    val wasUserDeleted = userRepository.deleteUser(email = email)

                    if (!wasUserDeleted) throw Exception(Constants.Server.Exceptions.UserNotFound)

                    call.respond(MessageResponse(message = Constants.Auth.AccountDeletionSuccess))
                }
            }

            get(Constants.Server.Routes.updateAccessToken) {
                safeCall {
                    val email = call.fetchEmailFromToken()
                    call.respondJwt(email = email)
                }
            }

            with(Constants.Server.Routes.Room) {
                post(create) {
                    safeCall {
                        val email = call.fetchEmailFromToken()
                        val receivedRoom = call.receive<Room>()
                        call.respond(
                            roomRepository.createRoom(email = email, room = receivedRoom)
                        )
                    }
                }
                get(get) {
                    safeCall {
                        val receivedRoomId = call.fetchParam(Constants.Server.Params.RoomId)
                        call.respond(
                            roomRepository.getRoomById(UUID(receivedRoomId))
                        )
                    }
                }
                patch(update) {
                    safeCall {
                        val email = call.fetchEmailFromToken()
                        val receivedRoom = call.receive<Room>()
                        val wasRoomUpdated = roomRepository.updateRoom(
                            email = email,
                            room = receivedRoom
                        )
                        call.respond(
                            MessageResponse(
                                message = if (wasRoomUpdated) {
                                    Constants.Server.ResponseMessages.RoomUpdateSuccess
                                } else {
                                    Constants.Server.ResponseMessages.RoomUpdateFailure
                                }
                            )
                        )
                    }
                }
                delete(delete) {
                    safeCall {
                        val receivedRoomId = call.fetchParam(Constants.Server.Params.RoomId)
                        val wasRoomDeleted = roomRepository.deleteRoom(UUID(receivedRoomId))
                        call.respond(
                            MessageResponse(
                                message = if (wasRoomDeleted) {
                                    Constants.Server.ResponseMessages.RoomDeleteSuccess
                                } else {
                                    Constants.Server.ResponseMessages.RoomDeleteFailure
                                }
                            )
                        )
                    }
                }
            }
        }
    }
}
