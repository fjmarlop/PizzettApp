package es.fjmarlop.pizzettApp.core.roomDB.models

import es.fjmarlop.pizzettApp.core.roomDB.entities.UserEntity

data class UserModel(val email: String, val name: String, val phone: String)


fun UserModel.toEntity() = UserEntity(email = email, name = name, phone = phone)