package service.facades

import dao.RestaurantDaoImpl
import org.example.readInt
import org.example.serializer
import org.example.validator
import service.enums.Role
import kotlin.system.exitProcess

class RestaurantFacade private constructor() {
    companion object {
        private var instance : RestaurantFacade? = null
        fun getInstance() : RestaurantFacade {
            if (instance == null) instance = RestaurantFacade()
            return instance!!
        }
    }
    suspend fun startWork() {
        val adminHandler = AdministratorFacade()
        val visitorHandler = VisitorFacade()
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

    fun regUser(): Boolean {
        var input: String
        do {
            println(
                "Выберите вашу роль, введя одно число: 1 - администратор, 2 - посетитель, " +
                        "или введите -1, чтобы прекратить работу программы."
            )
            input = readln()
        } while (input != "1" && input != "2" && input != "-1")
        if (input == "-1") finish()
        val role = when (input) {
            "1" -> Role.ADMIN
            else -> Role.VISITOR
        }
        do {
            println("Введите логин, состоящий только из латинских букв и цифр.")
            input = readln()
        } while (!validator.validateString(input))
        val login = input
        do {
            println("Введите пароль, состоящий только из латинских букв и цифр.")
            input = readln()
        } while (!validator.validateString(input))
        val password = input
        return RestaurantDaoImpl.getInstance().registerUser(role, login, password)
    }

    fun logInUser(): Boolean {
        var input: String
        do {
            println(
                "Введите логин, состоящий только из латинских букв и цифр, " +
                        "или введите -1, чтобы перейти к созданию аккаунта."
            )
            input = readln()
        } while (!validator.validateString(input) && input != "-1")
        if (input == "-1") return regUser()
        val login = input
        do {
            println("Введите пароль, состоящий только из латинских букв и цифр.")
            input = readln()
        } while (!validator.validateString(input))
        val password = input
        return RestaurantDaoImpl.getInstance().logInUser(login, password)
    }

    suspend fun deleteAcc() {
        val confirm = "Вы точно хотите удалить аккаунт? 1 - да, 2 - нет"
        var command: Int
        do command = readInt(confirm) while (command > 2 || command == 0)
        if (command == 2) return
        serializer.deleteUser(RestaurantDaoImpl.getInstance().currentUser!!.userId)
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
}