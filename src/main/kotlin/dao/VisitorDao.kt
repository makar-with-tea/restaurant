package dao

import entity.OrderEntity
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import service.Serializer
import service.exception.FileFailureException
import service.enums.OrderStatus
import service.enums.Priority

interface VisitorDao {
    fun startOrder(dishIds : ArrayList<Int> = arrayListOf()) : Boolean;
    suspend fun addDishToOrder(dishId : Int, orderId : Int) : Boolean;
    fun cancelOrder(orderId : Int) : Boolean;
    fun getStatus(orderId : Int) : OrderStatus;
    fun payForOrder(orderId : Int) : Boolean;
}

class VisitorDaoImpl : VisitorDao {
    override fun startOrder(dishIds : ArrayList<Int>) : Boolean {
        try {
            Serializer.getInstance().writeOrder(
                OrderEntity(
                    Serializer.getInstance().getMaxOrderId(), RestaurantDaoImpl.getInstance().currentUser!!.userId,
                    dishIds, OrderStatus.INPROGRESS
                )
            )
        } catch (e : FileFailureException) {
            println(e.message)
            return false
        }
        return true
    }
    override suspend fun addDishToOrder(dishId: Int, orderId: Int) : Boolean {
        try {
            val order = Serializer.getInstance().readOrder(orderId)
            order.dishIds.add(dishId)
            Serializer.getInstance().writeOrder(order)
            return true
        }
        catch (e : FileFailureException) {
            println(e.message)
            return false
        }
    }
    suspend fun finishOrder(orderId : Int) = runBlocking{
        launch {
            KitchenDaoImpl.getInstance().makeOrder(orderId)
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
        return Serializer.getInstance().readOrder(orderId).status
    }

    override fun payForOrder(orderId: Int): Boolean {
        TODO("Not yet implemented")
    }
}