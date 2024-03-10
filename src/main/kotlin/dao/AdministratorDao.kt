package dao

import entity.DishEntity
import entity.ReviewEntity

interface AdministratorDao {
    fun addDish(name : String, cookingTime : Long, price : Int, number : Int) : Boolean;
    fun deleteDish(dishId : Int) : Boolean;
    fun setNumber(dishId : Int, number : Int) : Boolean;
    fun setPrice(dishId : Int, price : Int) : Boolean;
    fun setCookingTime(dishId : Int, time : Long) : Boolean;
    fun getRevenue() : Int;
    fun getReviews() : ArrayList<ReviewEntity>;
}

class AdministratorDaoImpl(private val dishHandler: DishDaoImpl = DishDaoImpl()) : AdministratorDao {
    override fun addDish(name: String, cookingTime: Long, price: Int, number: Int): Boolean {
        try {
            RestaurantDaoImpl.getInstance().serializer.writeDish(DishEntity(
                RestaurantDaoImpl.getInstance().serializer.getMaxDishId(),
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
            RestaurantDaoImpl.getInstance().serializer.deleteDish(dishId)
            return true
        }
        catch (e : Exception) {
            println(e.message)
            return false
        }
    }

    override fun setNumber(dishId: Int, number: Int): Boolean {
        try {
            val dish = RestaurantDaoImpl.getInstance().serializer.readDish(dishId)
            dishHandler.setNumber(dish, number)
            RestaurantDaoImpl.getInstance().serializer.writeDish(dish)
            return true
        }
        catch (e : Exception) {
            println(e.message)
            return false
        }
    }

    override fun setPrice(dishId: Int, price: Int): Boolean {
        try {
            val dish = RestaurantDaoImpl.getInstance().serializer.readDish(dishId)
            dishHandler.setPrice(dish, price)
            RestaurantDaoImpl.getInstance().serializer.writeDish(dish)
            return true
        }
        catch (e : Exception) {
            println(e.message)
            return false
        }
    }

    override fun setCookingTime(dishId: Int, time: Long): Boolean {
        try {
            val dish = RestaurantDaoImpl.getInstance().serializer.readDish(dishId)
            dishHandler.setCookingTime(dish, time)
            RestaurantDaoImpl.getInstance().serializer.writeDish(dish)
            return true
        }
        catch (e : Exception) {
            println(e.message)
            return false
        }
    }

    override fun getRevenue(): Int {
        return RestaurantDaoImpl.getInstance().serializer.getRevenue()
    }

    override fun getReviews(): ArrayList<ReviewEntity> {
        try {
            return RestaurantDaoImpl.getInstance().serializer.getReviews()
        } catch (e : Exception) {
            println(e.message)
            return arrayListOf()
        }
    }
}