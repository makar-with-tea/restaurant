package dao

import entity.OrderEntity
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import service.Serializer
import service.exception.FileFailureException
import service.enums.OrderStatus

interface VisitorDao {
    fun startOrder(dishIds : ArrayList<Int> = arrayListOf()) : Boolean;
    fun addDishToOrder(dishId : Int, orderId : Int) : Boolean;
    suspend fun finishOrder(orderId : Int);
    fun cancelOrder(orderId : Int) : Boolean;
    fun getStatus(orderId : Int) : OrderStatus;
    fun payForOrder(orderId : Int) : Boolean;
    fun leaveReview(dishId : Int, mark : Int, comment : String);
}

class VisitorDaoImpl : VisitorDao {
    override fun startOrder(dishIds: ArrayList<Int>): Boolean {
        try {
            Serializer.getInstance().writeOrder(
                OrderEntity(
                    Serializer.getInstance().getMaxOrderId(), RestaurantDaoImpl.getInstance().currentUser!!.userId,
                    dishIds, OrderStatus.INPROGRESS
                )
            )
            return true
        } catch (e: FileFailureException) {
            println(e.message)
            return false
        }
    }

    override fun addDishToOrder(dishId: Int, orderId: Int): Boolean {
        try {
            val order = Serializer.getInstance().readOrder(orderId)
            order.dishIds.add(dishId)
            Serializer.getInstance().writeOrder(order)
            return true
        } catch (e: FileFailureException) {
            println(e.message)
            return false
        }
    }

    override suspend fun finishOrder(orderId: Int) {
        try {
            KitchenDaoImpl.getInstance().makeOrder(orderId)
        } catch (e: Exception) {
            println(e.message)
        }
    }

    override fun cancelOrder(orderId: Int): Boolean {
        try {
            Serializer.getInstance().deleteOrder(orderId)
            return true
        }
        catch (e : FileFailureException) {
            println(e.message)
            return false
        }
    }

    override fun getStatus(orderId: Int): OrderStatus {
        return try {
            Serializer.getInstance().readOrder(orderId).status
        } catch (e : FileFailureException) {
            OrderStatus.ERROR
        }
    }

    override fun payForOrder(orderId: Int) : Boolean {
        try {
            val order = Serializer.getInstance().readOrder(orderId)
            if (order.payed) return false
            var sum = 0
            for (dishId in order.dishIds) {
                val dish = Serializer.getInstance().readDish(dishId)
                sum += dish.price
            }
            Serializer.getInstance().updateRevenue(sum)
            order.payed = true
            Serializer.getInstance().writeOrder(order)
            return true
        } catch (e : FileFailureException) {
            println(e.message)
            return false
        }
    }

    override fun leaveReview(dishId : Int, mark : Int, comment : String) {

    }
}