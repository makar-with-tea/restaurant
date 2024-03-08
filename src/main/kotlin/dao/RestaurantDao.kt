package dao

interface RestaurantDao { // чел вошел в ресторан
    fun registerUser(login : String, password : String);
    fun logInUser(login : String, password : String) : Boolean;
}