package ru.levgrekov.data.repositories

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

abstract class SafeSuspendTransactionProvider {
    suspend fun <T> catchingDbQuery(block: suspend () -> T): Result<T> = runCatching {
        newSuspendedTransaction(Dispatchers.IO) { block() }
    }
}