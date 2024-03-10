package service.facades

import dao.RestaurantDaoImpl
import org.example.*
import service.Validator

class AdministratorFacade(private val validator : Validator = Validator()) {
    suspend fun adminMain() {
        val adminMes = """ДОСТУПНЫЕ КОМАНДЫ
            |1 - посмотреть меню
            |2 - добавить блюдо в меню
            |3 - удалить блюдо из меню
            |4 - изменить количество единиц блюда в ресторане
            |5 - изменить цену блюда
            |6 - изменить время приготовления блюда
            |7 - посмотреть прибыль ресторана
            |8 - зайти в другой аккаунт
            |9 - удалить аккаунт
            |10 - завершить работу приложения
        """.trimMargin()
        var command: Int
        while (true) {
            do command = readInt(adminMes) while (command > 10 || command < 1)
            when (command) {
                1 -> RestaurantFacade.getInstance().printMenu()
                2 -> addDish()
                3 -> deleteDish()
                4 -> setNumber()
                5 -> setPrice()
                6 -> setCookingTime()
                7 -> getRevenue()
                8 -> return RestaurantFacade.getInstance().startWork()
                9 -> return RestaurantFacade.getInstance().deleteAcc()
                10 -> return RestaurantFacade.getInstance().finish()
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