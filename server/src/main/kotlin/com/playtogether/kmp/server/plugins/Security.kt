package com.playtogether.kmp.server.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.playtogether.kmp.data.util.Constants
import io.ktor.server.application.Application
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import java.security.SecureRandom
import java.util.Date
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import org.koin.ktor.ext.get

data class TokenConfig(val issuer: String, val audience: String, val expiresIn: Long, val secret: String)
data class TokenClaim(val name: String, val value: String)
data class SaltedHash(val hash: String, val salt: String)

interface TokenService {
    /**
     * Generates and returns a JSON Web Token (JWT).
     * @param config configuration settings for token generation. Allows to configure the issuer, audience, expiry time
     * of the token and the server secret.
     * @param claims multiple token claims that is used to store information about a token.
     * @return A randomly generated JSON Web Token (JWT).
     */
    fun generateToken(config: TokenConfig, vararg claims: TokenClaim): String
}

interface HashingService {
    fun generateSaltedHash(value: String, saltLength: Int = 32): SaltedHash
    fun verify(value: String, saltedHash: SaltedHash): Boolean
}

class SHA256HashingService : HashingService {
    override fun generateSaltedHash(value: String, saltLength: Int): SaltedHash {
        val salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLength)
        val saltAsHex = Hex.encodeHexString(salt)
        val hash = DigestUtils.sha256Hex("$saltAsHex$value")
        return SaltedHash(
            hash = hash,
            salt = saltAsHex
        )
    }

    override fun verify(value: String, saltedHash: SaltedHash): Boolean {
        return DigestUtils.sha256Hex(saltedHash.salt + value) == saltedHash.hash
    }
}

class JWTService : TokenService {
    override fun generateToken(config: TokenConfig, vararg claims: TokenClaim): String {
        var token = JWT.create()
            .withAudience(config.audience)
            .withIssuer(config.issuer)
            .withExpiresAt(Date(System.currentTimeMillis() + config.expiresIn))
        claims.forEach { claim ->
            token = token.withClaim(claim.name, claim.value)
        }
        return token.sign(Algorithm.HMAC256(config.secret))
    }
}

fun Application.setupSecurity() {
    val tokenConfig = get<TokenConfig>()
    authentication {
        jwt {
            realm = this@setupSecurity.environment.config.property(
                Constants.Server.SecretKeys.JWTRealm
            ).getString()
            verifier(
                JWT
                    .require(Algorithm.HMAC256(tokenConfig.secret))
                    .withAudience(tokenConfig.audience)
                    .withIssuer(tokenConfig.issuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(tokenConfig.audience)) JWTPrincipal(credential.payload)
                else null
            }
        }
    }
}
