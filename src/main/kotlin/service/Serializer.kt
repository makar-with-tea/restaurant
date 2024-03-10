package service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import entity.DishEntity
import entity.OrderEntity
import entity.UserEntity
import service.exception.FileFailureException
import java.io.File

class Serializer {
    companion object {
        val mapper = ObjectMapper()
        private var instance: Serializer? = null
        fun getInstance() : Serializer {
            if (instance == null) instance = Serializer()
            return instance!!
        }
    }
    init {
        var folder = File("src\\main\\data")
        if (!folder.exists()) {
            try {
                folder.mkdirs()
            } catch (e: Exception) {
                throw FileFailureException("Ошибка при создании директории данных!")
            }
        }
        folder = File("src\\main\\data\\users")
        if (!folder.exists()) {
            try {
                folder.mkdirs()
            } catch (e: Exception) {
                throw FileFailureException("Ошибка при создании директории пользователей!")
            }
        }
        folder = File("src\\main\\data\\dishes")
        if (!folder.exists()) {
            try {
                folder.mkdirs()
            } catch (e: Exception) {
                throw FileFailureException("Ошибка при создании директории блюд!")
            }
        }
        folder = File("src\\main\\data\\orders")
        if (!folder.exists()) {
            try {
                folder.mkdirs()
            } catch (e: Exception) {
                throw FileFailureException("Ошибка при создании директории заказов!")
            }
        }
        folder = File("src\\main\\data\\maxIds")
        if (!folder.exists()) {
            try {
                folder.mkdirs()
            } catch (e: Exception) {
                throw FileFailureException("Ошибка при создании директории максимальных id!")
            }
        }
        val jsonFile = File("src\\main\\data\\revenue.json")
        mapper.writeValue(jsonFile, 0)
    }

    private fun updateMaxUserId() {
        val jsonFile = File("src\\main\\data\\maxIds\\maxUserId.json")
        mapper.writeValue(jsonFile, getMaxUserId() + 1)
    }
    fun getMaxUserId(): Int {
        val jsonFile = File("src\\main\\data\\maxIds\\maxUserId.json")
        if (!jsonFile.exists()) {
            return 0
        }
        val rootNode: JsonNode = mapper.readTree(jsonFile)
        return rootNode.asInt()
    }
    private fun updateMaxDishId() {
        val jsonFile = File("src\\main\\data\\maxIds\\maxDishId.json")
        mapper.writeValue(jsonFile, getMaxDishId() + 1)
    }
    fun getMaxDishId() : Int {
        val jsonFile = File("src\\main\\data\\maxIds\\maxDishId.json")
        if (!jsonFile.exists()) {
            return 0
        }
        val rootNode: JsonNode = mapper.readTree(jsonFile)
        return rootNode.asInt()
    }
    private fun updateMaxOrderId() {
        val jsonFile = File("src\\main\\data\\maxIds\\maxOrderId.json")
        mapper.writeValue(jsonFile, getMaxOrderId() + 1)
    }
    fun getMaxOrderId(): Int {
        val jsonFile = File("src\\main\\data\\maxIds\\maxOrderId.json")
        if (!jsonFile.exists()) {
            return 0
        }
        val rootNode: JsonNode = mapper.readTree(jsonFile)
        return rootNode.asInt()
    }
    fun getRevenue() : Int {
        val jsonFile = File("src\\main\\data\\revenue.json")
        if (!jsonFile.exists()) {
            return 0
        }
        val rootNode: JsonNode = mapper.readTree(jsonFile)
        return rootNode.asInt()
    }
    fun updateRevenue(newProfit : Int) {
        val jsonFile = File("src\\main\\data\\revenue.json")
        mapper.writeValue(jsonFile, getRevenue() + newProfit)
    }
    fun writeUser(user: UserEntity) {
        val jsonFile = File("src\\main\\data\\users\\user${user.userId}.json")
        try {
            mapper.writeValue(jsonFile, user)
        }
        catch (e : Exception) {
            throw FileFailureException("Ошибка при создании файла пользователя!")
        }
        if (user.userId == getMaxUserId()) updateMaxUserId()
    }
    fun readUser(userId: Int) : UserEntity {
        val jsonFile = File("src\\main\\data\\users\\user$userId.json")
        try {
            return mapper.readValue(jsonFile, UserEntity::class.java)
        }
        catch (e : Exception) {
            throw FileFailureException("Ошибка при чтении информации о пользователе!")
        }
    }
    fun deleteUser(userId: Int) {
        val jsonFile = File("src\\main\\data\\users\\user$userId.json")
        if (!jsonFile.exists()) throw FileFailureException("Ошибка при удалении файла пользователя!")
        try {
            jsonFile.delete()
        }
        catch (e : Exception) {
            throw FileFailureException("Ошибка при удалении файла пользователя!")
        }
    }
    fun writeDish(dish: DishEntity) {
        val jsonFile = File("src\\main\\data\\dishes\\dish${dish.dishId}.json")
        try {
            mapper.writeValue(jsonFile, dish)
        }
        catch (e : Exception) {
            throw FileFailureException("Ошибка при создании файла блюда!")
        }
        if (dish.dishId == getMaxDishId()) updateMaxDishId()
    }
    fun readDish(dishId: Int) : DishEntity {
        val jsonFile = File("src\\main\\data\\dishes\\dish$dishId.json")
        try {
            return mapper.readValue(jsonFile, DishEntity::class.java)
        }
        catch (e : Exception) {
            throw FileFailureException("Ошибка при чтении информации о блюде!")
        }
    }
    fun deleteDish(dishId: Int) {
        val jsonFile = File("src\\main\\data\\dishes\\dish$dishId.json")
        if (!jsonFile.exists()) throw FileFailureException("Ошибка при удалении файла блюда!")
        try {
            jsonFile.delete()
        }
        catch (e : Exception) {
            throw FileFailureException("Ошибка при удалении файла блюда!")
        }
    }
    fun writeOrder(order: OrderEntity) {
        val jsonFile = File("src\\main\\data\\orders\\order${order.orderId}.json")
        try {
            mapper.writeValue(jsonFile, order)
        }
        catch (e : Exception) {
            throw FileFailureException("Ошибка при создании файла заказа!")
        }
        if (order.orderId == getMaxOrderId()) updateMaxOrderId()
    }
    fun readOrder(orderId: Int) : OrderEntity {
        val jsonFile = File("src\\main\\data\\orders\\order$orderId.json")
        try {
            return mapper.readValue(jsonFile, OrderEntity::class.java)
        }
        catch (e : Exception) {
            throw FileFailureException("Ошибка при чтении информации о заказе!")
        }
    }
    fun deleteOrder(orderId: Int) {
        val jsonFile = File("src\\main\\data\\orders\\order$orderId.json")
        if (!jsonFile.exists()) throw FileFailureException("Ошибка при удалении файла заказа!")
        try {
            jsonFile.delete()
        }
        catch (e : Exception) {
            throw FileFailureException("Ошибка при удалении файла заказа!")
        }
    }
}