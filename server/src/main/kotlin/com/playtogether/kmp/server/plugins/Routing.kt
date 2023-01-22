package com.playtogether.kmp.server.plugins

import com.playtogether.kmp.data.models.server.AuthResponse
import com.playtogether.kmp.data.util.Constants
import com.playtogether.kmp.server.InvalidCredentialsException
import com.playtogether.kmp.server.InvalidEmailException
import com.playtogether.kmp.server.InvalidParameterException
import com.playtogether.kmp.server.InvalidPasswordException
import com.playtogether.kmp.server.RegexPatterns
import com.playtogether.kmp.server.repositories.AuthRepository
import com.playtogether.kmp.server.repositories.UserRepository
import com.playtogether.kmp.server.safeCall
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
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
                hash = user.hashedPassword,
                salt = user.salt
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

                val isValidEmail = RegexPatterns.Email.matcher(emailParam).matches()
                val isValidPassword = RegexPatterns.Password.matcher(passwordParam).matches()

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
    }
}
