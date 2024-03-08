package dao

import entity.DishEntity

interface AdministratorDao {
    fun addDish(dish : DishEntity) : Boolean;
    fun deleteDish(dishId : Int) : Boolean;
    fun setNumber(dishId : Int, number : Int) : Boolean;
    fun setPrice(dishId : Int, price : Int) : Boolean;
    fun setCookingTime(dishId : Int, time : Int) : Boolean;
}