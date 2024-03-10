package dao

import entity.DishEntity
import dao.DishDaoImpl
import org.example.serializer
import service.Serializer

interface AdministratorDao {
    fun addDish(name : String, cookingTime : Long, price : Int, number : Int) : Boolean;
    fun deleteDish(dishId : Int) : Boolean;
    fun setNumber(dishId : Int, number : Int) : Boolean;
    fun setPrice(dishId : Int, price : Int) : Boolean;
    fun setCookingTime(dishId : Int, time : Long) : Boolean;
    fun getRevenue() : Int;
}

class AdministratorDaoImpl(private val dishHandler: DishDaoImpl = DishDaoImpl()) : AdministratorDao {
    override fun addDish(name: String, cookingTime: Long, price: Int, number: Int): Boolean {
        try {
            serializer.writeDish(DishEntity(serializer.getMaxDishId(),
                name, cookingTime, price, number))
            return true
        }
        catch (e : Exception) {
            println(e.message)
            return false
        }
    }

    override fun deleteDish(dishId: Int): Boolean {
        try {
            serializer.deleteDish(dishId)
            return true
        }
        catch (e : Exception) {
            println(e.message)
            return false
        }
    }

    override fun setNumber(dishId: Int, number: Int): Boolean {
        try {
            val dish = serializer.readDish(dishId)
            dishHandler.setNumber(dish, number)
            serializer.writeDish(dish)
            return true
        }
        catch (e : Exception) {
            println(e.message)
            return false
        }
    }

    override fun setPrice(dishId: Int, price: Int): Boolean {
        try {
            val dish = serializer.readDish(dishId)
            dishHandler.setPrice(dish, price)
            serializer.writeDish(dish)
            return true
        }
        catch (e : Exception) {
            println(e.message)
            return false
        }
    }

    override fun setCookingTime(dishId: Int, time: Long): Boolean {
        try {
            val dish = serializer.readDish(dishId)
            dishHandler.setCookingTime(dish, time)
            serializer.writeDish(dish)
            return true
        }
        catch (e : Exception) {
            println(e.message)
            return false
        }
    }

    override fun getRevenue(): Int {
        return serializer.getRevenue()
    }

}