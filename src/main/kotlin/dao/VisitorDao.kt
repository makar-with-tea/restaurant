package dao

import entity.OrderEntity
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import service.Serializer
import service.Validator
import service.exception.FileFailureException
import service.enums.OrderStatus

interface VisitorDao {
    fun startOrder(dishIds : ArrayList<Int> = arrayListOf()) : Int;
    fun addDishToOrder(dishId : Int, orderId : Int) : Boolean;
    suspend fun finishOrder(orderId : Int);
    fun cancelOrder(orderId : Int) : Boolean;
    fun getStatus(orderId : Int) : OrderStatus;
    fun payForOrder(orderId : Int) : Boolean;
    fun leaveReview(dishId : Int, mark : Int, comment : String);
    fun getOrders() : ArrayList<Int>;
}

class VisitorDaoImpl(private val validator: Validator = Validator()) : VisitorDao {
    private val serializer : Serializer = Serializer()
    override fun startOrder(dishIds: ArrayList<Int>): Int {
        try {
            serializer.writeOrder(
                OrderEntity(
                    serializer.getMaxOrderId(), RestaurantDaoImpl.getInstance().currentUser!!.userId,
                    dishIds, OrderStatus.INPROGRESS
                )
            )
            return serializer.getMaxOrderId() - 1
        } catch (e: FileFailureException) {
            println(e.message)
            return -1
        }
    }

    override fun addDishToOrder(dishId: Int, orderId: Int): Boolean {
        try {
            val order = serializer.readOrder(orderId)
            if (!validator.validateUserForOrder(orderId)) return false
            order.dishIds.add(dishId)
            serializer.writeOrder(order)
            return true
        } catch (e: FileFailureException) {
            println(e.message)
            return false
        }
    }

    override suspend fun finishOrder(orderId: Int) {
        try {
            if (!validator.validateUserForOrder(orderId)) return
            RestaurantDaoImpl.getInstance().kitchen.makeOrder(orderId)
        } catch (e: Exception) {
            println(e.message)
        }
    }

    override fun cancelOrder(orderId: Int): Boolean {
        try {
            if (!validator.validateUserForOrder(orderId)) return false
            serializer.deleteOrder(orderId)
            return true
        }
        catch (e : FileFailureException) {
            println(e.message)
            return false
        }
    }

    override fun getStatus(orderId: Int): OrderStatus {
        return try {
            if (!validator.validateUserForOrder(orderId)) return OrderStatus.ERROR
            serializer.readOrder(orderId).status
        } catch (e : FileFailureException) {
            OrderStatus.ERROR
        }
    }

    override fun payForOrder(orderId: Int) : Boolean {
        try {
            val order = serializer.readOrder(orderId)
            if (order.payed) return false
            if (!validator.validateUserForOrder(orderId)) return false
            var sum = 0
            for (dishId in order.dishIds) {
                val dish = serializer.readDish(dishId)
                sum += dish.price
            }
            serializer.updateRevenue(sum)
            order.payed = true
            serializer.writeOrder(order)
            return true
        } catch (e : FileFailureException) {
            println(e.message)
            return false
        }
    }

    override fun leaveReview(dishId : Int, mark : Int, comment : String) {

    }

    override fun getOrders(): ArrayList<Int> {
        val res = arrayListOf<Int>()
        for (ind in 0..<serializer.getMaxOrderId()) {
            try {
                val order = serializer.readOrder(ind)
                if (order.visitorId == (RestaurantDaoImpl.getInstance().currentUser?.userId ?: return arrayListOf())) {
                    res.add(order.orderId)
                }
            }
            catch (e : FileFailureException) {
                continue
            }
        }
        return res
    }
}