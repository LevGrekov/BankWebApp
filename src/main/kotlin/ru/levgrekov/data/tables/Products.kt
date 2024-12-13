package ru.levgrekov.data.tables

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.date
import java.math.BigDecimal
import java.time.LocalDate


object Products : IntIdTable("products") {
    val client = reference("client", Users)
    val availableProductID = integer("availableProdId")
    val balance: Column<BigDecimal> = decimal("balance", 10, 2)
        .default(BigDecimal(0))
    val openingDate: Column<LocalDate> = date("opening_date")
        .default(LocalDate.now())

    val expirationDate: Column<LocalDate?> = date("expiration_date")
        .nullable()
        .default(null)
}

class Product(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Product>(Products)

    var availableProductID by Products.availableProductID
    var client by User referencedOn Products.client
    var balance by Products.balance
    var openingDate by Products.openingDate
    var expirationDate by Products.expirationDate
}