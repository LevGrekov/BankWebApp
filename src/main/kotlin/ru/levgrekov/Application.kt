package ru.levgrekov

import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import ru.levgrekov.auth.JwtService
import ru.levgrekov.data.repositories.AvailableProductsMemoryImpl
import ru.levgrekov.data.repositories.ProductsRepositoryExposedImpl
import ru.levgrekov.data.repositories.TransferRepositoryExposedImpl
import ru.levgrekov.data.repositories.UserRepositoryExposedImpl
import ru.levgrekov.plugins.configureDatabases
import ru.levgrekov.plugins.configureRouting
import ru.levgrekov.plugins.configureSecurity
import ru.levgrekov.plugins.configureSerialization
import ru.levgrekov.presentation.controllers.ProductController
import ru.levgrekov.presentation.controllers.TransferController
import ru.levgrekov.presentation.controllers.UserController

//DB_PASSWORD=1937;DB_POSTGRES_URL=jdbc:postgresql://localhost:5432/bank;DB_POSTGRES_USER=postgres;HASH_SECRET_KEY=233;JWT_SECRET=322;PORT=8080


fun main() {
    embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {

    val jwtService = JwtService()
    val userRepository = UserRepositoryExposedImpl()
    val productRepository = ProductsRepositoryExposedImpl()
    val availableProductRepository = AvailableProductsMemoryImpl()
    val transferRepository = TransferRepositoryExposedImpl()



    val userController = UserController(userRepository, jwtService)
    val productController = ProductController(availableProductRepository, productRepository)
    val transferController = TransferController(transferRepository)

    configureDatabases()
    configureSerialization()
    configureSecurity(jwtService.getVerifier(), userRepository::findUserByPhoneNumber)
    configureRouting(userController, productController, transferController)
}
