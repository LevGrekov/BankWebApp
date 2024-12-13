package ru.levgrekov.presentation.controllers

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.levgrekov.auth.JwtService
import ru.levgrekov.domain.logic.UserLogic
import ru.levgrekov.domain.repositories.UserRepository

class UserController(
    private val userRepository: UserRepository,
    private val jwtService: JwtService
) {
    suspend fun performAuthentication(call: ApplicationCall) {
        val receive = call.receive<UserLogic.LoginRequest>()
        userRepository.findUserByPhoneNumber(receive.phoneNumber).fold(
            onSuccess = {
                it?.let {
                    if (it.password == receive.password) {
                        call.respond(HttpStatusCode.OK, jwtService.generateToken(it))
                    } else {
                        call.respond(HttpStatusCode.Unauthorized, "Некоректный Пароль")
                    }
                } ?: run {
                    call.respond(HttpStatusCode.Unauthorized, "Пользователь не найден")
                }
            },
            onFailure = {
                call.respond(HttpStatusCode(418, "Сервер - это чайник"))
            }
        )
    }

    suspend fun performRegistration(call: ApplicationCall) {
        val receive = call.receive<UserLogic.RegisterRequest>()
        userRepository.findUserByPhoneNumber(receive.phoneNumber).fold(
            onSuccess = {
                it?.let {
                    call.respond(HttpStatusCode.Conflict, "Такой Пользователь уже существует")
                } ?: run {
                    userRepository.createUser(receive)
                    call.respond("Вы успешно зарегистрировались")
                }
            },
            onFailure = {
                call.respond(HttpStatusCode(418, "Сервер - это чайник"))
            }
        )
    }

    suspend fun getUserName(call: ApplicationCall) {
        call.principal<UserLogic.FullUserLogic>()?.let { user ->
            userRepository.getUserById(user.id).fold(
                onSuccess = { call.respond(HttpStatusCode.OK, (it as UserLogic.FullUserLogic).firstName) },
                onFailure = { call.respond(HttpStatusCode(418, "I'm a teapot"), "Сервер - это чайник") },
            )
        } ?: run {
            call.respond(HttpStatusCode.Unauthorized, "Вы не авторизованы")
        }
    }

}