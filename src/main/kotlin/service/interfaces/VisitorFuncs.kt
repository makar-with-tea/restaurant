package service.interfaces

import dao.RestaurantDaoImpl
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.example.*
import service.Validator
import service.Serializer
import service.enums.OrderStatus

class VisitorFuncs(private val validator : Validator = Validator()) {
    suspend fun visitorMain() {
        val adminMes = """ДОСТУПНЫЕ КОМАНДЫ
            |0 - посмотреть меню
            |1 - начать оформление заказа
            |2 - добавить блюдо из меню в заказ
            |3 - завершить оформление заказа
            |4 - отменить заказ
            |5 - узнать статус заказа
            |6 - оплатить заказ
            |7 - зайти в другой аккаунт
            |8 - удалить аккаунт
            |9 - посмотреть список id Ваших заказов
            |10 - завершить работу приложения
        """.trimMargin()
        var command: Int
        while (true) {
            do command = readInt(adminMes) while (command > 10)
            runBlocking {
                when (command) {
                    0 -> printMenu()
                    1 -> startOrder()
                    2 -> addDishToOrder()
                    3 -> launch { finishOrder()}
                    4 -> cancelOrder()
                    5 -> getStatus()
                    6 -> payForOrder()
                    7 -> {
                        startWork()
                        return@runBlocking
                    }
                    8 -> {
                        deleteAcc()
                        return@runBlocking
                    }
                    9 -> getOrders()
                    10 -> finish()
                    //leaveReview - только после оплаты
                }
            }
        }
    }
    private fun startOrder() {
        val dishIds : ArrayList<Int> = arrayListOf()
        var dishId : Int
        val mes = "Чтобы добавить блюдо в заказ, введите его id из меню:\n" +
                RestaurantDaoImpl.getInstance().getMenu() + "\nЧтобы закончить выбор блюд, введите -1"
        do {
            println("Ваш заказ: $dishIds")
            dishId = readInt(mes)
            if (validator.validateDishId(dishId)) dishIds.add(dishId)
            else if (dishId != -1) println("Блюда с таким id не существует!")
        } while (dishId != -1)
        val orderId = RestaurantDaoImpl.getInstance().visitor.startOrder(dishIds)
        if (orderId != -1) {
            println("Заказ с id $orderId успешно создан!")
        }
        else println("Ошибка при создании заказа!")
    }

    private fun addDishToOrder() {
        var mes = "Чтобы добавить блюдо в заказ, введите его id из меню:\n" + RestaurantDaoImpl.getInstance().getMenu()
        val dishId = readInt(mes)
        if (validator.validateDishId(dishId)) {
            mes = "Введите id вашего заказа:"
            val orderId = readInt(mes)

            if (!validator.validateOrderId(orderId)) return println("Заказа с таким id не существует!")
            if (!validator.validateUserForOrder(orderId)) return println("У Вас нет доступа к этому заказу!")
            if (RestaurantDaoImpl.getInstance().visitor.addDishToOrder(dishId, orderId)) {
                println("Блюдо успешно добавлено в заказ.")
            } else println("Ошибка при добавлении блюда!")
        } else println("Блюда с таким id не существует!")
    }
    private suspend fun finishOrder() {
        val mes = "Введите id вашего заказа:"
        val orderId = readInt(mes)
        if (!validator.validateOrderId(orderId)) return println("Заказа с таким id не существует!")
        if (!validator.validateUserForOrder(orderId)) return println("У Вас нет доступа к этому заказу!")
        RestaurantDaoImpl.getInstance().visitor.finishOrder(orderId)
        println("Заказ отправлен на кухню.")
    }
    private fun cancelOrder() {
        val mes = "Введите id вашего заказа:"
        val orderId = readInt(mes)
        if (!validator.validateOrderId(orderId)) return println("Заказа с таким id не существует!")
        if (!validator.validateUserForOrder(orderId)) return println("У Вас нет доступа к этому заказу!")
        if (RestaurantDaoImpl.getInstance().visitor.cancelOrder(orderId)) {
            println("Заказ успешно отменен.")
        } else println("Ошибка при отмене заказа!")
    }
    private fun getStatus() {
        val mes = "Введите id вашего заказа:"
        val orderId = readInt(mes)
        if (!validator.validateOrderId(orderId)) return println("Заказа с таким id не существует!")
        if (!validator.validateUserForOrder(orderId)) return println("У Вас нет доступа к этому заказу!")
        val res = RestaurantDaoImpl.getInstance().visitor.getStatus(orderId)
        if (res != OrderStatus.ERROR) {
            println("Статус заказа: $res.")
        } else println("Ошибка при запросе статуса заказа!")

    }
    private fun payForOrder() {
        val mes = "Введите id вашего заказа:"
        val orderId = readInt(mes)
        if (!validator.validateOrderId(orderId)) return println("Заказа с таким id не существует!")
        if (!validator.validateUserForOrder(orderId)) return println("У Вас нет доступа к этому заказу!")
        if (RestaurantDaoImpl.getInstance().visitor.payForOrder(orderId)) {
            println("Заказ успешно оплачен. Хотите ли оставить отзыв? 1 - да, 2 - нет")
        } else println("Ошибка при оплате заказа!")
    }
    private fun leaveReview(){}//(dishId : Int, mark : Int, comment : String);
    private fun getOrders() {
        println("Id Ваших заказов: ${RestaurantDaoImpl.getInstance().visitor.getOrders()}")
    }
}