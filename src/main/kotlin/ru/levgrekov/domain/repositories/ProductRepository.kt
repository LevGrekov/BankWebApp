package ru.levgrekov.domain.repositories

import ru.levgrekov.domain.logic.ProductLogic

interface ProductRepository {
    suspend fun getProductById(id: Int): Result<ProductLogic.Full?>
    suspend fun createProduct(product: ProductLogic): Result<Int>
    suspend fun getProductsByClientId(clientId: Int): Result<List<ProductLogic.Short>>
}