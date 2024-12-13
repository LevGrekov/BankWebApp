package ru.levgrekov.data.tables

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate

object Transfers : IntIdTable("transfers") {
    val senderAccount = reference("sender_account", Products)

    val receiverAccount = reference("receiver_account", Products)

    val amount: Column<Double> = double("amount")

    val timestamp: Column<LocalDate> = date("timestamp")
        .default(LocalDate.now())

    val transferType: Column<String> = varchar("transfer_type", 255)

}

class Transfer(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Transfer>(Transfers)

    var senderAccountId by Product referencedOn Transfers.senderAccount
    var receiverAccountId by Product referencedOn Transfers.receiverAccount

    var amount by Transfers.amount
    var timestamp by Transfers.timestamp
    var transferType by Transfers.transferType
}