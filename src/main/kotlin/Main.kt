package org.example

import dao.KitchenDaoImpl
import dao.RestaurantDaoImpl
import entity.OrderEntity
import entity.UserEntity
import service.*
import service.enums.Role
import kotlinx.coroutines.*
import service.Validator
import service.enums.OrderStatus
import service.interfaces.AdminFuncs
import service.interfaces.VisitorFuncs
import kotlin.system.exitProcess


suspend fun startWork() {
    val adminHandler = AdminFuncs()
    val visitorHandler = VisitorFuncs()
    val regMes = """Здравствуйте! Чтобы начать работу с приложением, зарегистрируйтесь или войдите в аккаунт.
    Введите одно из чисел:
    1 - Зарегистрироваться
    2 - Войти"""
    var input: String
    do {
        println(regMes)
        input = readln()
    } while (input != "1" && input != "2")
    when (input) {
        "1" -> while (!regUser()) println("Не получилось зарегистрироваться, попробуйте снова.")
        "2" -> while (!logInUser()) println("Не получилось войти, попробуйте снова.")
    }
    if (RestaurantDaoImpl.getInstance().currentUser!!.role == Role.ADMIN) adminHandler.adminMain()
    else visitorHandler.visitorMain()
}
fun regUser() : Boolean {
    var input : String
    do {
        println("Выберите вашу роль, введя одно число: 1 - администратор, 2 - посетитель, " +
                "или введите -1, чтобы прекратить работу программы.")
        input = readln()
    } while (input != "1" && input != "2" && input != "-1")
    if (input == "-1") finish()
    val role = when(input) {
        "1" -> Role.ADMIN
        else -> Role.VISITOR
    }
    do {
        println("Введите логин, состоящий только из латинских букв и цифр.")
        input = readln()
    } while (!Validator.getInstance().validateString(input))
    val login = input
    do {
        println("Введите пароль, состоящий только из латинских букв и цифр.")
        input = readln()
    } while (!Validator.getInstance().validateString(input))
    val password = input
    return RestaurantDaoImpl.getInstance().registerUser(role, login, password)
}

fun logInUser() : Boolean {
    var input : String
    do {
        println("Введите логин, состоящий только из латинских букв и цифр, " +
                "или введите -1, чтобы перейти к созданию аккаунта.")
        input = readln()
    } while (!Validator.getInstance().validateString(input) && input != "-1")
    if (input == "-1") return regUser()
    val login = input
    do {
        println("Введите пароль, состоящий только из латинских букв и цифр.")
        input = readln()
    } while (!Validator.getInstance().validateString(input))
    val password = input
    return RestaurantDaoImpl.getInstance().logInUser(login, password)
}

suspend fun deleteAcc() {
    val confirm = "Вы точно хотите удалить аккаунт? 1 - да, 2 - нет"
    var command : Int
    do command = readInt(confirm) while (command > 2 || command == 0)
    if (command == 2) return
    Serializer.getInstance().deleteUser(RestaurantDaoImpl.getInstance().currentUser!!.userId)
    RestaurantDaoImpl.getInstance().currentUser = null
    startWork()
}

fun printMenu() {
    println(RestaurantDaoImpl.getInstance().getMenu())
}

fun finish() {
    println("Спасибо за пользование нашим приложением!")
    exitProcess(0)
}

fun readInt(mes : String) : Int {
    var str : String
    do {
        println(mes)
        str = readln()
    } while (!Validator.getInstance().validateNumber(str))
    return str.toInt()
}

fun readLong(mes : String) : Long {
    var str : String
    do {
        println(mes)
        str = readln()
    } while (!Validator.getInstance().validateNumber(str))
    return str.toLong()
}

fun f() {
    for (e in 1..10) println(e)
}
suspend fun exec(i: Int) {
    println("start$i")
    delay(100)
    println("end$i")
}
suspend fun main() {
    /*runBlocking {
        for (i in 1..10) {
            launch {exec(i)}
        }
    }
    var command : Int
    while (true) {
        do command = readInt("aaa") while (command > 9)
        when (command) {
            0 -> printMenu()
            1 -> f()
            2 -> finish()
        }
    }*/
    startWork()
}