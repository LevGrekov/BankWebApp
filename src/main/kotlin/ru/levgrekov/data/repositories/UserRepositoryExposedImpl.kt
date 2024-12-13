package ru.levgrekov.data.repositories

import ru.levgrekov.data.tables.User
import ru.levgrekov.data.tables.Users
import ru.levgrekov.domain.repositories.UserRepository
import ru.levgrekov.domain.logic.UserLogic

class UserRepositoryExposedImpl : SafeSuspendTransactionProvider(), UserRepository {

    override suspend fun getUserById(id: Int): Result<UserLogic?> = catchingDbQuery {
        User.findById(id)?.let {
            UserLogic.FullUserLogic(
                it.id.value,
                it.phoneNumber,
                it.password,
                it.firstname,
                it.secondName,
                it.thirdName,
                it.birthdate,
                it.regDate
            )
        } ?: run { null }
    }

    override suspend fun createUser(user: UserLogic): Result<Int> = catchingDbQuery {
        user as UserLogic.RegisterRequest
        User.new {
            this.phoneNumber = user.phoneNumber
            this.password = user.password
            this.firstname = user.firstName
            this.secondName = user.secondName
            this.birthdate = user.birthdate
            this.thirdName = user.thirdName
        }.id.value
    }

    override suspend fun findUserByPhoneNumber(phoneNumber: String): Result<UserLogic?> = catchingDbQuery {
        User.find{Users.phoneNumber eq phoneNumber}.singleOrNull()?.let {
            UserLogic.FullUserLogic(
                it.id.value,
                it.phoneNumber,
                it.password,
                it.firstname,
                it.secondName,
                it.thirdName,
                it.birthdate,
                it.regDate
            )
        } ?: run { null }
    }




}