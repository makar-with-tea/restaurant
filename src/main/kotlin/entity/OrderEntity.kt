package entity

import service.enums.OrderStatus
import service.enums.Priority

data class OrderEntity(
    val orderId : Int,
    val visitorId : Int,
    var dishIds : ArrayList<Int>,
    var status : OrderStatus,
    var priority : Priority
)