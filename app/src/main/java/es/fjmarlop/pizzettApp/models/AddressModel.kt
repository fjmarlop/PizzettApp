package es.fjmarlop.pizzettApp.models

import es.fjmarlop.pizzettApp.entities.AddressEntity

data class AddressModel(
    val name: String,
    val address: String,
    val city: String,
    val codPostal: String
)

fun AddressModel.toAddressEntity() = AddressEntity(name = name, address = address, city = city, codPostal = codPostal)
