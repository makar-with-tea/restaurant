package entity

import entity.AdministratorEntity
import entity.VisitorEntity

data class RestaurantEntity(
    var menu : MutableMap<Int, DishEntity> = mutableMapOf(),
    var profit : Int = 0,
    var admins : ArrayList<AdministratorEntity> = arrayListOf(),
    var visitors : ArrayList<VisitorEntity> = arrayListOf()
)