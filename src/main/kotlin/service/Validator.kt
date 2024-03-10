package service

import com.fasterxml.jackson.databind.ObjectMapper
import dao.RestaurantDaoImpl
import service.Serializer
import service.exception.FileFailureException

class Validator {
    private val alphabet: String = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    private val serializer = Serializer()

    private fun isDigit(arg: String): Boolean {
        if (arg.isEmpty()) return false
        for (i in 1..<arg.length) {
            if (!arg[i].isDigit()) {
                return false
            }
        }
        return arg[0] == '-' || arg[0].isDigit()
    }

    fun validateString(str: String): Boolean {
        for (e in str) {
            if (!alphabet.contains(e)) return false
        }
        return true
    }

    fun validateNumber(str: String): Boolean {
        return isDigit(str) && str.toLong() >= -1
    }

    fun validateDishId(dishId: Int) : Boolean {
        try {
            serializer.readDish(dishId)
            return true
        }
        catch (e: FileFailureException) {
            return false
        }
    }

    fun validateOrderId(orderId: Int) : Boolean {
        try {
            serializer.readOrder(orderId)
            return true
        }
        catch (e: FileFailureException) {
            return false
        }
    }

    fun validateUserForOrder(orderId: Int) : Boolean {
        if (!validateOrderId(orderId)) return false
        return serializer.readOrder(orderId).visitorId ==
                (RestaurantDaoImpl.getInstance().currentUser?.userId ?: return false)
    }
}