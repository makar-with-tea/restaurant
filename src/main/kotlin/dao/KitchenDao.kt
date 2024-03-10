package dao

import entity.DishEntity
import entity.OrderEntity
import kotlinx.coroutines.*
import service.Serializer
import service.enums.OrderStatus
import service.exception.*

interface KitchenDao {
    suspend fun makeOrder(orderId : Int)
}

class KitchenDaoImpl : KitchenDao {
    private val dishHandler: DishDaoImpl = DishDaoImpl()
    private val serializer: Serializer = Serializer()

    override suspend fun makeOrder(orderId: Int) {
        try {
            var ind = 0
            var order: OrderEntity
            var dish: DishEntity
            do {
                order = serializer.readOrder(orderId)
                if (order.status == OrderStatus.INPROGRESS) {
                    order.status = OrderStatus.COOKING
                    serializer.writeOrder(order)
                }
                if (order.dishIds.size == 0) break
                dish = serializer.readDish(order.dishIds[ind])
                if (dish.number == 0) {
                    order.status = OrderStatus.DENIED
                    serializer.writeOrder(order)
                    throw RanOutOfDishException("Блюдо ${dish.name} закончилось!")
                }
                println("Блюдо ${dish.name} из заказа $orderId готово.")
                dishHandler.cookPortion(dish)
                serializer.writeDish(dish)
                ++ind
                delay(dish.cookingTime)
            } while (ind < order.dishIds.size)
            println("Заказ $orderId готов.")
            order.status = OrderStatus.READY
            serializer.writeOrder(order)
        } catch (e: Exception) {
            println(e.message)
        }
    }
}