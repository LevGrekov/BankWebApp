package ru.levgrekov.domain.logic

import io.ktor.server.auth.*
import kotlinx.serialization.Serializable
import ru.levgrekov.plugins.LocalDateSerializer
import java.time.LocalDate

@Serializable
sealed interface UserLogic : Principal {
    val phoneNumber: String
    val password: String

    @Serializable
    data class FullUserLogic(
        val id: Int,
        override val phoneNumber: String,
        override val password: String,
        val firstName: String,
        val secondName: String,
        val thirdName: String?,
        @Serializable(with = LocalDateSerializer::class)
        val birthdate: LocalDate?,
        @Serializable(with = LocalDateSerializer::class)
        val regDate: LocalDate
    ) : UserLogic

    @Serializable
    data class RegisterRequest(
        override val phoneNumber: String,
        override val password: String,
        val firstName: String,
        val secondName: String,
        val thirdName: String?,
        @Serializable(with = LocalDateSerializer::class)
        val birthdate: LocalDate?,
    ) : UserLogic

    @Serializable
    data class LoginRequest(
        override val phoneNumber: String,
        override val password: String
    ) : UserLogic
}



