package ru.levgrekov.domain.logic

import kotlinx.serialization.Serializable

@Serializable
sealed class AvailableProductLogic {

    @Serializable
    data class Full(
        val id: Int,
        val name: String,
        val description: String,
        val imagePath: String,
        val text: String,
        val productType: ProductType
    ) : AvailableProductLogic()

    @Serializable
    data class Short(
        val id: Int,
        val name: String,
        val description: String,
        val imagePath: String,
        val productType: ProductType
    ) : AvailableProductLogic()
}

enum class ProductType {
    CARD,
    DEPOSIT,
    SAVING_ACCOUNT
}



