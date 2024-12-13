package ru.levgrekov.plugins

import com.auth0.jwt.JWTVerifier
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import ru.levgrekov.domain.logic.UserLogic

fun Application.configureSecurity(
    jwtVerifier: JWTVerifier,
    findUserByPhone: suspend (String) -> Result<UserLogic?>
) {
    authentication {
        jwt("jwt") {
            verifier(jwtVerifier)
            realm = "Service server"
            validate { jwtCred ->
                val payload = jwtCred.payload
                val phoneNumber = payload.getClaim("phoneNumber").asString()
                val user = findUserByPhone(phoneNumber)
                user.fold(
                    onSuccess = { it },
                    onFailure = { null }
                )
            }
        }
    }
}
