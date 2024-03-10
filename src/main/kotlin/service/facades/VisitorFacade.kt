package service.facades

import dao.RestaurantDaoImpl
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.example.*
import service.Validator
import service.enums.OrderStatus

class VisitorFacade(private val validator : Validator = Validator()) {
    fun visitorMain() {
        val adminMes = """ДОСТУПНЫЕ КОМАНДЫ
            |1 - посмотреть меню
            |2 - начать оформление заказа
            |3 - добавить блюдо из меню в заказ
            |4 - завершить оформление заказа
            |5 - отменить заказ
            |6 - узнать статус заказа
            |7 - оплатить заказ
            |8 - зайти в другой аккаунт
            |9 - удалить аккаунт
            |10 - посмотреть список id Ваших заказов
            |11 - завершить работу приложения
        """.trimMargin()
        var command: Int
        while (true) {
            do command = readInt(adminMes) while (command > 11 || command < 1)
            when (command) {
                1 -> RestaurantFacade.getInstance().printMenu()
                2 -> startOrder()
                3 -> addDishToOrder()
                4 -> finishOrder()
                5 -> cancelOrder()
                6 -> getStatus()
                7 -> payForOrder()
                8 -> return RestaurantFacade.getInstance().startWork()
                9 -> return RestaurantFacade.getInstance().deleteAcc()
                10 -> getOrders()
                11 -> RestaurantFacade.getInstance().finish()
                //leaveReview - только после оплаты
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
    private fun finishOrder() {
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
            var ans : Int
            do {
                ans = readInt("Заказ успешно оплачен. Хотите ли оставить отзыв? 1 - да, 2 - нет")
            } while (ans < 1 || ans > 2)
            if (ans == 1) return leaveReview()
        } else println("Ошибка при оплате заказа!")
    }
    private fun leaveReview() {
        while (true) {
            var mes = "Введите Id блюда, на которое хотите оставить отзыв:"
            var dishId: Int
            do {
                dishId = readInt(mes)
                mes = "Блюда с таким id не существует! Введите Id блюда, на которое хотите оставить отзыв:"
            } while (!validator.validateDishId(dishId))
            mes = "Введите Вашу оценку от 1 до 5:"
            var mark: Int
            do {
                mark = readInt(mes)
            } while (mark < 1 || mark > 5)
            println("Введите ваш комментарий по данному блюду:")
            val comment = readln()
        }
    }//(dishId : Int, mark : Int, comment : String);

    private fun getOrders() {
        println("Id Ваших заказов: ${RestaurantDaoImpl.getInstance().visitor.getOrders()}")
    }
}