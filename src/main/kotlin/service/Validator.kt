package service

import com.fasterxml.jackson.databind.ObjectMapper
import service.Serializer
import service.exception.FileFailureException

class Validator {
    companion object {
        private var instance: Validator? = null
        fun getInstance(): Validator {
            if (instance == null) instance = Validator()
            return instance!!
        }
    }

    private val alphabet: String = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

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
            Serializer.getInstance().readDish(dishId)
            return true
        }
        catch (e: FileFailureException) {
            return false
        }
    }

    fun validateOrderId(orderId: Int) : Boolean {
        try {
            Serializer.getInstance().readOrder(orderId)
            return true
        }
        catch (e: FileFailureException) {
            return false
        }
    }
}