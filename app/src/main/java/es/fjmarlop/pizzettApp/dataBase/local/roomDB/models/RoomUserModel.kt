package es.fjmarlop.pizzettApp.dataBase.local.roomDB.models

import es.fjmarlop.pizzettApp.dataBase.local.roomDB.entities.UserEntity

data class UserModel(val email: String, val name: String, val phone: String)


fun UserModel.toEntity() = UserEntity(email = email, name = name, phone = phone)