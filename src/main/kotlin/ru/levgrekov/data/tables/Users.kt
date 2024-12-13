package ru.levgrekov.data.tables

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate

object Users : IntIdTable("users") {
    val phoneNumber: Column<String> = varchar("phone_number", 20).uniqueIndex()
    val password: Column<String> = varchar("password", 255)
    val firstName: Column<String> = varchar("firstname", 255)
    val secondName: Column<String> = varchar("secondname", 255)
    val thirdName: Column<String?> = varchar("thirdname", 255).nullable()
    val birthdate: Column<LocalDate?> = date("birthdate").nullable()
    val regDate: Column<LocalDate> = date("regdate").default(LocalDate.now())
}


class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(Users)

    var phoneNumber by Users.phoneNumber
    var password by Users.password

    var firstname by Users.firstName
    var secondName by Users.secondName
    var thirdName by Users.thirdName

    var birthdate by Users.birthdate
    var regDate by Users.regDate
}