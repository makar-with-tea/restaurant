package org.example.dao

interface DishDao {
    fun setNumber(number : Int) : Boolean;
    fun setPrice(price : Int) : Boolean;
    fun setCookingTime(time : Int) : Boolean;
    fun getNumber() : Int;
    fun getPrice() : Int;
    fun getCookingTime() : Int;
}

class DishDaoImpl : DishDao {
    override fun setNumber(number: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun setPrice(price: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun setCookingTime(time: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun getNumber(): Int {
        TODO("Not yet implemented")
    }

    override fun getPrice(): Int {
        TODO("Not yet implemented")
    }

    override fun getCookingTime(): Int {
        TODO("Not yet implemented")
    }

}