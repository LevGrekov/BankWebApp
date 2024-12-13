package ru.levgrekov.presentation.routes

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import ru.levgrekov.presentation.controllers.UserController


fun Route.userRoute(controller: UserController){
    post("api/v1/signup") {
        controller.performRegistration(call)
    }

    post("api/v1/login") {
        controller.performAuthentication(call)
    }

    authenticate("jwt") {
        get("api/v1/username"){
            controller.getUserName(call)
        }
    }
}
