package service.interfaces

import dao.RestaurantDaoImpl
import kotlinx.coroutines.runBlocking
import org.example.*
import service.Validator

class AdminFuncs(private val validator : Validator = Validator()) {
    fun adminMain() {
        val adminMes = """ДОСТУПНЫЕ КОМАНДЫ
            |0 - посмотреть меню
            |1 - добавить блюдо в меню
            |2 - удалить блюдо из меню
            |3 - изменить количество единиц блюда в ресторане
            |4 - изменить цену блюда
            |5 - изменить время приготовления блюда
            |6 - посмотреть прибыль ресторана
            |7 - зайти в другой аккаунт
            |8 - удалить аккаунт
            |9 - завершить работу приложения
        """.trimMargin()
        var command: Int
        while (true) {
            do command = readInt(adminMes) while (command > 9)
            when (command) {
                0 -> printMenu()
                1 -> addDish()
                2 -> deleteDish()
                3 -> setNumber()
                4 -> setPrice()
                5 -> setCookingTime()
                6 -> getRevenue()
                7 -> return startWork()
                8 -> return deleteAcc()
                9 -> return finish()
            }
        }
    }

    private fun addDish() {
        var name: String
        do {
            println("Введите название блюда, состоящее только из латинских букв и цифр.")
            name = readln()
        } while (!validator.validateString(name))
        val cookingTime = readLong("Введите время приготовления блюда в миллисекундах.")
        val price = readInt("Введите число - цену блюда в рублях.")
        val number = readInt("Введите число - количество единиц блюда в ресторане.")
        if (RestaurantDaoImpl.getInstance().admin.addDish(name, cookingTime, price, number)) println("Успешно!")
        else println("Добавление не удалось, попробуйте снова.")
    }

    private fun deleteDish() {
        val dishId = readInt(
            "Введите число - номер блюда в меню.\nМЕНЮ:\n" +
                    RestaurantDaoImpl.getInstance().getMenu()
        )
        if (RestaurantDaoImpl.getInstance().admin.deleteDish(dishId)) println("Успешно!")
        else println("Удаление не удалось, попробуйте снова.")
    }

    private fun setNumber() {
        val dishId = readInt(
            "Введите число - номер блюда в меню.\nМЕНЮ:\n" +
                    RestaurantDaoImpl.getInstance().getMenu()
        )
        val number = readInt("Введите число - обновленное количество единиц блюда в ресторане.")
        if (RestaurantDaoImpl.getInstance().admin.setNumber(dishId, number)) println("Успешно!")
        else println("Изменение не удалось, попробуйте снова.")
    }

    private fun setPrice() {
        val dishId = readInt(
            "Введите число - номер блюда в меню.\nМЕНЮ:\n" +
                    RestaurantDaoImpl.getInstance().getMenu()
        )
        val price = readInt("Введите число - обновленную цену блюда в рублях.")
        if (RestaurantDaoImpl.getInstance().admin.setPrice(dishId, price)) println("Успешно!")
        else println("Изменение не удалось, попробуйте снова.")
    }

    private fun setCookingTime() {
        val dishId = readInt(
            "Введите число - номер блюда в меню.\nМЕНЮ:\n" +
                    RestaurantDaoImpl.getInstance().getMenu()
        )
        val cookingTime = readLong("Введите число - обновленное время приготовления блюда в миллисекундах.")
        if (RestaurantDaoImpl.getInstance().admin.setCookingTime(dishId, cookingTime)) println("Успешно!")
        else println("Изменение не удалось, попробуйте снова.")
    }

    private fun getRevenue() {
        println("Выручка ресторана: ${RestaurantDaoImpl.getInstance().admin.getRevenue()} рублей.")
    }
}