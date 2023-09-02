package es.fjmarlop.pizzettApp.models

import es.fjmarlop.pizzettApp.entities.roomEntities.AddressEntity

data class AddressModel(
    val id: Int,
    val name: String,
    val address: String,
    val city: String,
    val codPostal: String,
    val email: String
)

fun AddressModel.toAddressEntity() = AddressEntity(id = id, name = name, address = address, city = city, codPostal = codPostal, email = email)
