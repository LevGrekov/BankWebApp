package ru.levgrekov.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import ru.levgrekov.data.tables.Products
import ru.levgrekov.data.tables.Transfers
import ru.levgrekov.data.tables.Users
import java.sql.DriverManager

fun Application.configureDatabases() {
    val dbUrl = System.getenv("DB_POSTGRES_URL")
    val dbUser = System.getenv("DB_POSTGRES_USER")
    val dbPassword = System.getenv("DB_PASSWORD")


    Database.connect({ DriverManager.getConnection(dbUrl, dbUser, dbPassword) })
    transaction {
        SchemaUtils.createMissingTablesAndColumns(
            Users, Transfers, Products,
            inBatch = false,
            withLogs = false,
        )
    }
}

