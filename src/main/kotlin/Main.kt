package org.example

interface RestaurantDao {
    fun registerAdmin(login : String, password : String);
    fun registerVisitor(login : String, password : String);
    fun logInAdmin(login : String, password : String) : Boolean;
    fun logInVisitor(login : String, password : String) : Boolean;
}
interface AdministratorDao {
    fun addDish(dish : DishEntity) : Boolean;
    fun deleteDish(dishId : Int) : Boolean;
    fun setNumber(dishId : Int, number : Int) : Boolean;
    fun setPrice(dishId : Int, price : Int) : Boolean;
    fun setCookingTime(dishId : Int, time : Int) : Boolean;
}
interface VisitorDao {
    fun addDishToOrder(dishId : Int, visitorId : Int) : Boolean;
    fun cancelOrder(orderId : Int) : Boolean;
    fun getStatus(orderId : Int) : OrderStatus;
    fun payForOrder(orderId : Int) : Boolean;
}
interface KitchenDao {
    fun cookDish(dishId : Int) : Boolean; // --dish.number

}
interface DishDao {
    fun setNumber(number : Int) : Boolean;
    fun setPrice(price : Int) : Boolean;
    fun setCookingTime(time : Int) : Boolean;
    fun getNumber() : Int;
    fun getPrice() : Int;
    fun getCookingTime() : Int;
}
//___________________________________________________________
data class DishEntity(
    val dishId : Int,
    var name : String,
    var cookingTime : Int,
    var price : Int,
    var number : Int
)
data class OrderEntity(
    val orderId : Int,
    val visitorId : Int,
    var dishIds : ArrayList<Int>,
    var status : OrderStatus,
    var priority : Priority
)
data class RestaurantEntity(
    var menu : ArrayList<DishEntity>,
    var profit : Int,
    var admins : ArrayList<AdministratorEntity>,
    var visitors : ArrayList<VisitorEntity>
)
data class AdministratorEntity(
    val adminId : Int,
    val login : String,
    val password : String // encoded somehow??
)
data class VisitorEntity(
    val visitorId : Int,
    val login : String,
    val password : String
)
//___________________________________________________________
enum class OrderStatus {
    ACCEPTED,
    COOKING,
    READY,
    DENIED
}
enum class Priority {
    HIGH,
    NORMAL,
    LOW
}
//___________________________________________________________
/*class AdministratorDaoImpl : AdministratorDao {
    override fun addDish(dish : DishEntity) : Boolean {return true;}
    override fun deleteDish(dishId : Int) : Boolean {return true;}
    override fun setNumber(dishId : Int, number : Int) : Boolean {return true;}
    override fun setPrice(dishId : Int, price : Int) : Boolean {return true;}
    override fun setCookingTime(dishId : Int, time : Int) : Boolean {return true;}
}*/
/*class RuntimeRestaurantDao() : RestaurantDao {
    override fun registerAdmin(login : String, password : String) {}
    override fun registerVisitor(login : String, password : String) {}
    override fun logInAdmin(login : String, password : String) : Boolean { return true; }
    override fun logInVisitor(login : String, password : String) : Boolean { return true; }
}*/
//class VisitorDaoImpl : VisitorDao {}
fun main() {
    val name = "Kotlin"
    println("Hello, $name!")

    for (i in 1..5) {
        println("i = $i")
    }
}