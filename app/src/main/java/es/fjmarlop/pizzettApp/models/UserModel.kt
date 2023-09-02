package es.fjmarlop.pizzettApp.models

import es.fjmarlop.pizzettApp.entities.roomEntities.UserEntity

data class UserModel(val email: String, val name: String, val phone: String)


fun UserModel.toEntity() = UserEntity(email = email, name = name, phone = phone)