package ru.levgrekov.data.repositories

import ru.levgrekov.data.tables.availableCards
import ru.levgrekov.domain.logic.AvailableProductLogic
import ru.levgrekov.domain.repositories.AvailableProductRepository

class AvailableProductsMemoryImpl : AvailableProductRepository {
    override suspend fun getAllAvailableProducts(): Result<List<AvailableProductLogic.Short>> =
        runCatching {
            availableCards.map {
                AvailableProductLogic.Short(
                    id = it.id,
                    description = it.description,
                    name = it.name,
                    imagePath = it.imagePath,
                    productType = it.productType
                )
            }
        }

    override suspend fun getAvailableProductById(id: Int): Result<AvailableProductLogic.Full?> =
        runCatching { availableCards.firstOrNull { it.id == id } }
}