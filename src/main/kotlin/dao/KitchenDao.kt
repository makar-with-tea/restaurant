package dao

import entity.OrderEntity
import entity.RestaurantEntity
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

interface KitchenDao { // работа с потоками, mediator or command
    fun makeOrder(order: OrderEntity); // --dish.number; поток
    //fun cookDish(dishId: Int) : Boolean;
}

class KitchenDaoImpl : KitchenDao {
    override fun makeOrder(order: OrderEntity): Unit = runBlocking {
        val channel = Channel<Int>()
        launch {
            val rest = RestaurantEntity()
            for (dishId in channel) {
                delay(rest.menu[dishId]?.cookingTime!!)
            }
        }

        for (dishId in order.dishIds) {
            channel.send(dishId)
        }
        channel.close()
    }

    //fun cookDish(dishId: Int) : Boolean;
}