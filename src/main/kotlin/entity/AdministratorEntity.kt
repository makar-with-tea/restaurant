package entity

data class AdministratorEntity(
    val adminId : Int,
    val login : String,
    val password : String // encoded somehow??
)