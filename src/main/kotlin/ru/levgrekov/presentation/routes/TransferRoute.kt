package ru.levgrekov.presentation.routes

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import ru.levgrekov.presentation.controllers.TransferController

fun Route.transferRoute(controller: TransferController){
    authenticate("jwt") {
        route("api/v1/transfers"){
            post{
                controller.newTransfer(call)
            }
        }
    }
}