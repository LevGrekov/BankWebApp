package ru.levgrekov.data.repositories

import ru.levgrekov.data.tables.*
import ru.levgrekov.domain.logic.ProductLogic
import ru.levgrekov.domain.repositories.ProductRepository

class ProductsRepositoryExposedImpl : SafeSuspendTransactionProvider() ,ProductRepository {

    override suspend fun getProductById(id: Int): Result<ProductLogic.Full?> = catchingDbQuery {
        Product.findById(id)?.let {
            availableCards.firstOrNull { ap -> ap.id == it.availableProductID }?.let { ap ->
                ProductLogic.Full(
                    id = it.id.value,
                    name = ap.name,
                    clientId = it.client.id.value,
                    type = ap.productType.name,
                    balance = it.balance.toDouble(),
                    openingDate = it.openingDate,
                    expirationDate = it.expirationDate
                )
            }
        }
    }


    override suspend fun createProduct(product: ProductLogic): Result<Int> = catchingDbQuery {
        product as ProductLogic.ExposedProduct
        Product.new {
            this.availableProductID = product.availableProductID
            this.client = User[product.clientId]
            this.balance = product.balance.toBigDecimal()
            this.openingDate = product.openingDate
            this.expirationDate = product.expirationDate
        }.id.value
    }

    override suspend fun getProductsByClientId(clientId: Int): Result<List<ProductLogic.Short>> = catchingDbQuery {
        Product.find { Products.client eq clientId }.mapNotNull {
            availableCards.firstOrNull { ap -> ap.id == it.availableProductID }?.let { ap ->
                ProductLogic.Short(
                    id = it.id.value,
                    name = ap.name,
                    type = ap.productType.name,
                    balance = it.balance.toDouble()
                )
            }
        }
    }
}