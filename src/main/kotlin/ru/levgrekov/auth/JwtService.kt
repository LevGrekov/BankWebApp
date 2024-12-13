package ru.levgrekov.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import ru.levgrekov.domain.logic.UserLogic
import java.time.LocalDateTime
import java.time.ZoneOffset

class JwtService {

    private val issuer = "bank_compose"
    private val jwtSecret = System.getenv("JWT_SECRET")
    private val algorithm = Algorithm.HMAC512(jwtSecret)

    private val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    fun generateToken(user: UserLogic): String {
        return JWT.create()
            .withSubject("CardsAppAuthentification")
            .withIssuer(issuer)
            .withClaim("phoneNumber", user.phoneNumber)
            .withExpiresAt(LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.UTC))
            .sign(algorithm)
    }

    fun getVerifier(): JWTVerifier = verifier
}