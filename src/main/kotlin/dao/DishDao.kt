package dao

import entity.DishEntity

interface DishDao {
    fun setNumber(dish : DishEntity, number : Int);
    fun setPrice(dish : DishEntity, price : Int);
    fun setCookingTime(dish : DishEntity, time : Long);
    fun getNumber(dish : DishEntity) : Int;
    fun getPrice(dish : DishEntity) : Int;
    fun getCookingTime(dish : DishEntity) : Long;
}

class DishDaoImpl : DishDao {
    override fun setNumber(dish: DishEntity, number: Int) {
        dish.number = number
    }

    override fun setPrice(dish: DishEntity, price: Int) {
        dish.price = price
    }

    override fun setCookingTime(dish: DishEntity, time: Long) {
        dish.cookingTime = time
    }

    override fun getNumber(dish: DishEntity): Int {
        return dish.number
    }

    override fun getPrice(dish: DishEntity): Int {
        return dish.price
    }

    override fun getCookingTime(dish: DishEntity): Long {
        return dish.cookingTime
    }

    fun cookPortion(dish: DishEntity) : Boolean {
        if (dish.number > 0) {
            --dish.number
            return true
        }
        return false
    }

}