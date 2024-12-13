package ru.levgrekov.data.repositories

import ru.levgrekov.data.tables.Product
import ru.levgrekov.data.tables.Transfer
import ru.levgrekov.data.tables.Transfers
import ru.levgrekov.domain.logic.TransferLogic
import ru.levgrekov.domain.repositories.TransferRepository

class TransferRepositoryExposedImpl : SafeSuspendTransactionProvider() ,TransferRepository {

    override suspend fun createTransfer(transfer: TransferLogic.FullTransferLogic) = catchingDbQuery {
        Transfer.new {
            this.senderAccountId = Product[transfer.senderAccountId]
            this.receiverAccountId = Product[transfer.receiverAccountId]
            this.amount = transfer.amount
            this.timestamp = transfer.timestamp
            this.transferType = transfer.transferType
        }.also {
            it.senderAccountId.balance -= it.amount.toBigDecimal()
            it.receiverAccountId.balance += it.amount.toBigDecimal()
        }.id.value

    }

    override suspend fun getTransfersBySenderAccountId(senderAccountId: Int): Result<List<TransferLogic>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTransfersByReceiverAccountId(receiverAccountId: Int): Result<List<TransferLogic>> {
        TODO("Not yet implemented")
    }

}