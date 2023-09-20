package es.fjmarlop.pizzettApp.core.roomDB.models

import es.fjmarlop.pizzettApp.core.roomDB.entities.AddressEntity

data class AddressModel(
    val id: Int,
    val name: String,
    val address: String,
    val city: String,
    val codPostal: String,
    val email: String
)

fun AddressModel.toAddressEntity() = AddressEntity(id = id, name = name, address = address, city = city, codPostal = codPostal, email = email)
