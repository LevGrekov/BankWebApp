package ru.levgrekov.presentation.controllers

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.levgrekov.domain.logic.ProductLogic
import ru.levgrekov.domain.logic.UserLogic
import ru.levgrekov.domain.repositories.AvailableProductRepository
import ru.levgrekov.domain.repositories.ProductRepository
import java.time.LocalDate

class ProductController(
    private val availableProductRepository: AvailableProductRepository,
    private val productRepository: ProductRepository
) {
    suspend fun getAllUsersProducts(call: ApplicationCall) {
        call.principal<UserLogic.FullUserLogic>()?.let { user ->
            productRepository.getProductsByClientId(user.id).fold(
                onSuccess = { call.respond(HttpStatusCode.OK, it) },
                onFailure = { call.respond(HttpStatusCode(418, "I'm a teapot"), "Сервер - это чайник") },
            )
        } ?: run {
            call.respond(HttpStatusCode.Unauthorized, "Вы не авторизованы")
        }
    }

    suspend fun getAllAvailableProducts(call: ApplicationCall) {
        availableProductRepository.getAllAvailableProducts().fold(
            onSuccess = { list -> call.respond(HttpStatusCode.OK, list) },
            onFailure = { call.respond(HttpStatusCode(418, "I'm a teapot"), "Сервер - это чайник") }
        )
    }

    suspend fun getAvailableProduct(call: ApplicationCall) {
        call.parameters["id"]?.toIntOrNull()?.let { id ->
            availableProductRepository.getAvailableProductById(id).fold(
                onSuccess = {
                    it?.let {
                        call.respond(HttpStatusCode.OK, it)
                    } ?: run {
                        call.respond(HttpStatusCode.BadRequest, "Неизвестный id продукта")
                    }
                },
                onFailure = { call.respond(HttpStatusCode(418, "I'm a teapot"), "Сервер - это чайник") }
            )
        } ?: run {
            call.respond(HttpStatusCode.BadRequest, "Неизвестный ID продукта")
        }
    }

    suspend fun addNewProductToUser(call: ApplicationCall) {
        val productID = call.receive<Int>()
        call.principal<UserLogic.FullUserLogic>()?.let { user ->
            availableProductRepository.getAvailableProductById(productID).fold(
                onSuccess = { ap ->
                    ap?.let {
                        productRepository.createProduct(
                            ProductLogic.ExposedProduct(
                                id = 0,
                                availableProductID = ap.id,
                                clientId = user.id,
                                balance = 5000.0,
                                openingDate = LocalDate.now(),
                                expirationDate = LocalDate.now().plusYears(2)
                            )
                        ).fold(
                            onSuccess = { id ->
                                call.respond(HttpStatusCode.OK, id)
                            },
                            onFailure = {ex ->
                                call.respond(HttpStatusCode(418, "I'm a teapot"), ex.message.toString())
                            }
                        )

                    } ?: run {
                        call.respond(HttpStatusCode.BadRequest, "Неизвестный id продукта")
                    }
                },
                onFailure = {ex->
                    call.respond(HttpStatusCode(418, "I'm a teapot"), ex.message.toString())
                }
            )
        } ?: run {
            call.respond(HttpStatusCode.Unauthorized, "Вы не авторизованы")
        }
    }

    suspend fun getUserProduct(call: ApplicationCall) {
        call.parameters["id"]?.toIntOrNull()?.let { id ->
            productRepository.getProductById(id).fold(
                onSuccess = {
                    it?.let {
                        call.respond(HttpStatusCode.OK, it)
                    } ?: run {
                        call.respond(HttpStatusCode.BadRequest, "Неизвестный id продукта")
                    }
                },
                onFailure = {
                    call.respond(HttpStatusCode(418, "I'm a teapot"), "Сервер - это чайник")
                }
            )
        } ?: run {
            call.respond(HttpStatusCode.BadRequest, "Неизвестный id продукта")
        }
    }
}