package org.example.service

import com.fasterxml.jackson.databind.ObjectMapper
import service.Serializer

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
        for (ch in arg) {
            if (!ch.isDigit()) {
                return false
            }
        }
        return true
    }

    fun validateString(str: String): Boolean {
        for (e in str) {
            if (!str.contains(e)) return false
        }
        return true
    }

    fun validateNumber(str: String): Boolean {
        return isDigit(str) && str.toLong() >= 0
    }
}