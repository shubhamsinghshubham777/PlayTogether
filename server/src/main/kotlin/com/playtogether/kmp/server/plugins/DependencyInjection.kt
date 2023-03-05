package com.playtogether.kmp.server.plugins

import com.playtogether.kmp.data.util.Constants
import com.playtogether.kmp.server.repositories.AuthRepository
import com.playtogether.kmp.server.repositories.AuthRepositoryImpl
import com.playtogether.kmp.server.repositories.RoomRepository
import com.playtogether.kmp.server.repositories.RoomRepositoryImpl
import com.playtogether.kmp.server.repositories.UserRepository
import com.playtogether.kmp.server.repositories.UserRepositoryImpl
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.setupDependencyInjection() {
    val securityModule = module {
        single<HashingService> { SHA256HashingService() }

        single<TokenService> { JWTService() }

        single {
            TokenConfig(
                issuer = environment.config.property(Constants.Server.SecretKeys.JWTIssuer)
                    .getString(),
                audience = environment.config.property(Constants.Server.SecretKeys.JWTAudience)
                    .getString(),
                expiresIn = 1000L * 60L * 15L, // 1 second * 1 minute * 15 = 15 minutes
                secret = System.getenv(Constants.Server.SecretKeys.JWTSecret)
            )
        }
    }

    val repositoryModule = module {
        single<UserRepository> { UserRepositoryImpl() }
        single<AuthRepository> { AuthRepositoryImpl() }
        single<RoomRepository> { RoomRepositoryImpl() }
    }

    install(Koin) {
        slf4jLogger()
        modules(
            securityModule,
            repositoryModule
        )
    }
}
