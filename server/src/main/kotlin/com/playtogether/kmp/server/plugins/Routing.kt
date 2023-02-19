package com.playtogether.kmp.server.plugins

import com.playtogether.kmp.data.models.server.AuthResponse
import com.playtogether.kmp.data.util.Constants
import com.playtogether.kmp.data.util.RegexPatterns
import com.playtogether.kmp.server.InvalidCredentialsException
import com.playtogether.kmp.server.InvalidEmailException
import com.playtogether.kmp.server.InvalidParameterException
import com.playtogether.kmp.server.InvalidPasswordException
import com.playtogether.kmp.server.PTException
import com.playtogether.kmp.server.repositories.AuthRepository
import com.playtogether.kmp.server.repositories.UserRepository
import com.playtogether.kmp.server.safeCall
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receiveMultipart
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.util.pipeline.PipelineContext
import org.koin.ktor.ext.inject


fun Application.setupRouting() {
    install(ContentNegotiation) { json() }

    val authRepository by inject<AuthRepository>()
    val userRepository by inject<UserRepository>()
    val tokenService by inject<TokenService>()
    val tokenConfig by inject<TokenConfig>()
    val hashingService by inject<HashingService>()

    suspend fun PipelineContext<Unit, ApplicationCall>.respondWithJWT(
        emailParam: String,
        passwordParam: String,
    ) {
        val user = userRepository.getUserByEmail(emailParam)
        val isValidHashPassword = hashingService.verify(
            value = passwordParam,
            saltedHash = SaltedHash(
                hash = user.hashedPassword
                    ?: throw PTException(Constants.Server.Exceptions.InvalidHashPassword),
                salt = user.salt
                    ?: throw PTException(Constants.Server.Exceptions.InvalidSalt)
            )
        )

        if (isValidHashPassword) {
            val jwt = tokenService.generateToken(
                config = tokenConfig,
                TokenClaim(
                    name = Constants.Server.JWTClaimEmail,
                    value = user.email
                )
            )

            call.respond(AuthResponse(token = jwt, user = user))
        } else throw InvalidCredentialsException
    }

    routing {
        post(Constants.Server.Routes.login) {
            safeCall {
                val emailParam = call.parameters[Constants.Server.Params.UserEmail]
                    ?: throw InvalidParameterException(Constants.Server.Params.UserEmail)

                val passwordParam = call.parameters[Constants.Server.Params.UserPassword]
                    ?: throw InvalidParameterException(Constants.Server.Params.UserPassword)

                respondWithJWT(
                    emailParam = emailParam,
                    passwordParam = passwordParam
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

                respondWithJWT(
                    emailParam = emailParam,
                    passwordParam = passwordParam,
                )
            }
        }

        authenticate {
            patch(Constants.Server.Routes.updateUserProfile) {
                safeCall {
                    val multipartData = call.receiveMultipart()

                    val principal = call.principal<JWTPrincipal>()

                    val email = principal?.getClaim(
                        Constants.Server.JWTClaimEmail,
                        String::class
                    ) ?: throw PTException(Constants.Server.Exceptions.InvalidAuthToken)

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
        }
    }
}
