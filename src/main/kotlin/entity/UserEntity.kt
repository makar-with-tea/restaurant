package entity

import service.enums.Role

data class UserEntity(
    val userId : Int = 0,
    val role : Role = Role.VISITOR,
    val login : String = "",
    val password : String = ""
)