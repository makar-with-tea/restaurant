package dao

import service.enums.OrderStatus

interface VisitorDao {
    fun setOrder()
    fun addDishToOrder(dishId : Int, visitorId : Int) : Boolean;
    fun cancelOrder(orderId : Int) : Boolean;
    fun getStatus(orderId : Int) : OrderStatus;
    fun payForOrder(orderId : Int) : Boolean;
}