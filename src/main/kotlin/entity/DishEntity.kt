package entity

import kotlin.time.Duration

data class DishEntity(
    val dishId : Int,
    var name : String,
    var cookingTime : Duration,
    var price : Int,
    var number : Int
)