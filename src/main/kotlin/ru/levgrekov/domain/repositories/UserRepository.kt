package ru.levgrekov.domain.repositories

import ru.levgrekov.domain.logic.UserLogic

interface UserRepository {
    suspend fun getUserById(id: Int): Result<UserLogic?>
    suspend fun createUser(user: UserLogic): Result<Int>
    suspend fun findUserByPhoneNumber(phoneNumber: String): Result<UserLogic?>
}