package ru.levgrekov.presentation.routes

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import ru.levgrekov.presentation.controllers.ProductController

fun Route.productRoute(controller: ProductController) {
    route("api/v1/products") {
        authenticate("jwt") {
            get {
                controller.getAllUsersProducts(call)
            }
            get("{id}") {
                controller.getUserProduct(call)
            }
            post {
                controller.addNewProductToUser(call)
            }
        }
    }

    route("/api/v1/available_products") {
        get {
            controller.getAllAvailableProducts(call)
        }
        get("{id}") {
            controller.getAvailableProduct(call)
        }
    }
}
