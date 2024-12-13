package ru.levgrekov.presentation.controllers

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.levgrekov.domain.logic.TransferLogic
import ru.levgrekov.domain.logic.UserLogic
import ru.levgrekov.domain.repositories.TransferRepository
import java.time.LocalDate

class TransferController(
    private val transferRepository: TransferRepository
) {
    suspend fun newTransfer(call: ApplicationCall) {
        val receive = call.receive<TransferLogic.NewTransfer>()
        call.principal<UserLogic.FullUserLogic>()?.let { user ->

            transferRepository.createTransfer(
                TransferLogic.FullTransferLogic(
                    id = 0,
                    senderAccountId = user.id,
                    receiverAccountId = receive.receiverAccountId,
                    amount = receive.amount,
                    timestamp = LocalDate.now(),
                    transferType = receive.transferType
                )
            ).fold(
                onSuccess = {
                    call.respond(HttpStatusCode.BadRequest,"1")
                },
                onFailure = {
                    call.respond(HttpStatusCode.BadRequest,it.stackTrace.toString())
                }
            )
        } ?: run {
            call.respond(HttpStatusCode.Unauthorized,"Как вы сюда попали ?")
        }
    }
}