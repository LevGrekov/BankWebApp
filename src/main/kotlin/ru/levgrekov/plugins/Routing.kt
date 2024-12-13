package ru.levgrekov.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.levgrekov.presentation.controllers.UserController
import ru.levgrekov.presentation.controllers.ProductController
import ru.levgrekov.presentation.controllers.TransferController
import ru.levgrekov.presentation.routes.productRoute
import ru.levgrekov.presentation.routes.transferRoute
import ru.levgrekov.presentation.routes.userRoute

fun Application.configureRouting(
    userController: UserController,
    productController: ProductController,
    transferController: TransferController) {
    routing {
        userRoute(userController)
        productRoute(productController)
        transferRoute(transferController)
    }
}
