package ru.levgrekov.domain.repositories

import ru.levgrekov.domain.logic.TransferLogic

interface TransferRepository {
    suspend fun createTransfer(transfer: TransferLogic.FullTransferLogic): Result<Int>
    suspend fun getTransfersBySenderAccountId(senderAccountId: Int): Result<List<TransferLogic>>
    suspend fun getTransfersByReceiverAccountId(receiverAccountId: Int): Result<List<TransferLogic>>
}