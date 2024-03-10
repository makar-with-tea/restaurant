package dao

import entity.DishEntity
import entity.OrderEntity
import kotlinx.coroutines.*
import service.Serializer
import service.enums.OrderStatus
import service.exception.*

interface KitchenDao { // работа с потоками, mediator or command
    suspend fun makeOrder(orderId : Int); // --dish.number; поток
    //fun cookDish(dishId: Int) : Boolean;
}

class KitchenDaoImpl private constructor() : KitchenDao {
    private val dishHandler: DishDaoImpl = DishDaoImpl()

    companion object {
        private var instance: KitchenDaoImpl? = null
        fun getInstance(): KitchenDaoImpl {
            if (instance == null) instance = KitchenDaoImpl()
            return instance!!
        }
    }

    override suspend fun makeOrder(orderId: Int) {
        try {
            var ind: Int = 0
            var order: OrderEntity
            var dish: DishEntity
            do {
                println("$orderId $ind")
                order = Serializer.getInstance().readOrder(orderId)
                if (order.status == OrderStatus.INPROGRESS) {
                    order.status = OrderStatus.COOKING
                    Serializer.getInstance().writeOrder(order)
                }
                dish = Serializer.getInstance().readDish(order.dishIds[ind])
                println(dish)
                if (dish.number == 0) {
                    order.status = OrderStatus.DENIED
                    Serializer.getInstance().writeOrder(order)
                    throw RanOutOfDishException("Блюдо ${dish.name} закончилось!")
                }
                --dish.number
                Serializer.getInstance().writeDish(dish)
                ++ind
                delay(dish.cookingTime)
            } while (ind < order.dishIds.size)
            order.status = OrderStatus.READY
        } catch (e: Exception) {
            println(e.message)
        }
    }
}
