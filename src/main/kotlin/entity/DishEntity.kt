package entity

data class DishEntity(
    val dishId : Int = 0,
    var name : String = "",
    var cookingTime : Long = 0L,
    var price : Int = 0,
    var number : Int = 0
) {
    constructor() : this(0, "", 0L, 0, 0) {}
}