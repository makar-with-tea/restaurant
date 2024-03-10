package dao

import entity.OrderEntity
import entity.UserEntity
import service.enums.Role
import service.Serializer
import service.exception.FileFailureException

interface RestaurantDao { // чел вошел в ресторан
    fun registerUser(role: Role, login: String, password: String): Boolean;
    fun logInUser(login: String, password: String): Boolean;
}

class RestaurantDaoImpl private constructor() : RestaurantDao {
    val admin: AdministratorDaoImpl = AdministratorDaoImpl()
    val visitor : VisitorDaoImpl = VisitorDaoImpl()
    var currentUser : UserEntity? = null
    companion object {
        private var instance : RestaurantDaoImpl? = null
        fun getInstance() : RestaurantDaoImpl {
            if (instance == null) instance = RestaurantDaoImpl()
            return instance!!
        }
    }
    override fun registerUser(role : Role, login: String, password: String) : Boolean {
        try {
            currentUser = UserEntity(Serializer.getInstance().getMaxUserId(), role, login, password)
            Serializer.getInstance().writeUser(
                UserEntity(Serializer.getInstance().getMaxUserId(), role, login, password))
            return true
        }
        catch (e : Exception) {
            println(e.message)
            return false
        }
    }

    override fun logInUser(login: String, password: String): Boolean {
        for (ind in 0..<Serializer.getInstance().getMaxUserId()) {
            try {
                val user = Serializer.getInstance().readUser(ind)
                if (user.login == login && user.password == password) {
                    currentUser = user
                    return true
                }
            }
            catch (e : FileFailureException) {
                continue
            }
        }
        return false
    }

    fun getMenu() : String {
        val menu : ArrayList<String> = arrayListOf()
        for (i in 0..<Serializer.getInstance().getMaxDishId()) {
            try {
                menu.add(Serializer.getInstance().readDish(i).toString())
            }
            catch (e : FileFailureException) { continue }
        }
        return menu.joinToString("\n")
    }

}