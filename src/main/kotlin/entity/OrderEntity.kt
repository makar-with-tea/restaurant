package entity

import service.enums.OrderStatus
import service.enums.Priority

data class OrderEntity(
    val orderId : Int = 0,
    val visitorId : Int = 0,
    var dishIds : ArrayList<Int> = arrayListOf(),
    var status : OrderStatus = OrderStatus.INPROGRESS,
    var priority : Priority = Priority.NORMAL,
    var payed : Boolean = false
)