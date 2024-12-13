package ru.levgrekov.domain.logic

import kotlinx.serialization.Serializable
import ru.levgrekov.plugins.LocalDateSerializer
import java.time.LocalDate

@Serializable
sealed interface ProductLogic {
    val id: Int
    @Serializable
    data class Full(
        override val id: Int,
        val name: String,
        val clientId: Int,
        val type: String,
        val balance: Double,
        @Serializable(with = LocalDateSerializer::class)
        val openingDate: LocalDate,
        @Serializable(with = LocalDateSerializer::class)
        val expirationDate: LocalDate?
    ) : ProductLogic

    @Serializable
    data class Short(
        override val id: Int,
        val name: String,
        val type: String,
        val balance: Double,
    ) : ProductLogic

    @Serializable
    data class ExposedProduct(
        override val id: Int,
        val availableProductID: Int,
        val clientId: Int,
        val balance: Double,
        @Serializable(with = LocalDateSerializer::class)
        val openingDate: LocalDate,
        @Serializable(with = LocalDateSerializer::class)
        val expirationDate: LocalDate,
    ) : ProductLogic
}