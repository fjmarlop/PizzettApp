package es.fjmarlop.pizzettApp.dataBase.local.models

import es.fjmarlop.pizzettApp.dataBase.local.entities.AddressEntity

data class AddressModel(
    val id: Int,
    val name: String,
    val address: String,
    val city: String,
    val codPostal: String,
    val email: String
)

fun AddressModel.toAddressEntity() = AddressEntity(id = id, name = name, address = address, city = city, codPostal = codPostal, email = email)
