package org.example

import dao.KitchenDaoImpl
import dao.RestaurantDaoImpl
import entity.OrderEntity
import entity.UserEntity
import service.Serializer
import service.enums.Role
import kotlinx.coroutines.*
import org.example.service.Validator

fun regUser() : Boolean {
    var input : String
    do {
        println("Выберите вашу роль, введя одно число: 1 - администратор, 2 - посетитель.")
        input = readln()
    } while (input != "1" && input != "2")
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
        println("Введите логин, состоящий только из латинских букв и цифр.")
        input = readln()
    } while (!Validator.getInstance().validateString(input))
    val login = input
    do {
        println("Введите пароль, состоящий только из латинских букв и цифр.")
        input = readln()
    } while (!Validator.getInstance().validateString(input))
    val password = input
    return RestaurantDaoImpl.getInstance().logInUser(login, password)
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

class AdminFuncs {
    fun addDish() : Boolean {
        var name: String
        do {
            println("Введите название блюда, состоящее только из латинских букв и цифр.")
            name = readln()
        } while (!Validator.getInstance().validateString(name))
        val cookingTime = readLong("Введите время приготовления блюда в миллисекундах.")
        val price = readInt("Введите число - цену блюда в рублях.")
        val number = readInt("Введите число - количество единиц блюда в ресторане.")
        return RestaurantDaoImpl.getInstance().admin.addDish(name, cookingTime, price, number)
    }
    fun deleteDish() : Boolean {
        val dishId = readInt("Введите число - номер блюда в меню.\nМЕНЮ:\n" +
                RestaurantDaoImpl.getInstance().getMenu())
        return RestaurantDaoImpl.getInstance().admin.deleteDish(dishId)
    }
    fun setNumber(dishId : Int, number : Int) : Boolean {

    }
    fun setPrice(dishId : Int, price : Int) : Boolean;
    fun setCookingTime(dishId : Int, time : Long) : Boolean;
}

suspend fun main() {
    /*val login : String = readln()
    val password : String = readln()
    val role = when(readln()) {
        "1" -> Role.ADMIN
        else -> Role.VISITOR
    }*/
    runBlocking {
        RestaurantDaoImpl.getInstance().logInUser("a", "aaaa")
        //RestaurantDaoImpl.getInstance().admin.addDish("a", 100, 10, 100)
        //RestaurantDaoImpl.getInstance().admin.addDish("b", 100, 13, 100)
        //RestaurantDaoImpl.getInstance().admin.addDish("c", 1000, 14, 100)
        //val order : OrderEntity = OrderEntity(0, 0, -1, arrayListOf(0, 1, 2))
        //KitchenDaoImpl.getInstance().makeOrder(order)
        RestaurantDaoImpl.getInstance().visitor.startOrder(arrayListOf(0, 0, 0))
        RestaurantDaoImpl.getInstance().visitor.startOrder(arrayListOf(0, 0, 2))
        launch {
            RestaurantDaoImpl.getInstance().visitor.finishOrder(1)
        }
        launch {
            RestaurantDaoImpl.getInstance().visitor.finishOrder(0)
        }
        RestaurantDaoImpl.getInstance().visitor.addDishToOrder(1, 0)
        //RestaurantDaoImpl.getInstance().visitor.cancelOrder(0)
        RestaurantDaoImpl.getInstance().visitor.addDishToOrder(0, 1)
        RestaurantDaoImpl.getInstance().visitor.addDishToOrder(0, 0)
    }



    val regMes = """"Здравствуйте! Чтобы начать работу с приложением, зарегистрируйтесь или войдите в аккаунт.
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



}