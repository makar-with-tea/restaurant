package org.example

import service.Serializer
import service.Validator
import service.facades.RestaurantFacade

val validator = Validator()
val serializer = Serializer()

fun readInt(mes : String) : Int {
    var str : String
    do {
        println(mes)
        str = readln()
    } while (!validator.validateNumber(str))
    return str.toInt()
}

fun readLong(mes : String) : Long {
    var str : String
    do {
        println(mes)
        str = readln()
    } while (!validator.validateNumber(str))
    return str.toLong()
}

fun main() {
    RestaurantFacade.getInstance().startWork()
}