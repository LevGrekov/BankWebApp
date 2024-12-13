package ru.levgrekov.domain.repositories

import ru.levgrekov.domain.logic.AvailableProductLogic

interface AvailableProductRepository {
    suspend fun getAllAvailableProducts(): Result<List<AvailableProductLogic.Short>>
    suspend fun getAvailableProductById(id: Int): Result<AvailableProductLogic.Full?>
}