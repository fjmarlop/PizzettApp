package es.fjmarlop.pizzettApp.dataBase.local.models

import es.fjmarlop.pizzettApp.dataBase.local.entities.UserEntity

/**
 * @author Fco Javier Marmolejo.
 * Modelo de datos para la base de datos interna de la aplicación que almacena
 * datos del usuario.
 * */
data class UserModel(val email: String, val name: String, val phone: String)

/**
 * Mappear de UserEntity a UserModel
 * @return UserEntity
 * */
fun UserModel.toEntity() = UserEntity(email = email, name = name, phone = phone)