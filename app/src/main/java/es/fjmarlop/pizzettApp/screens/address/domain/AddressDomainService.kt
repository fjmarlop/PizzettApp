package es.fjmarlop.pizzettApp.screens.address.domain

import es.fjmarlop.pizzettApp.core.roomDB.models.AddressModel
import es.fjmarlop.pizzettApp.core.roomDB.models.toAddressEntity
import es.fjmarlop.pizzettApp.screens.address.data.AddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AddressDomainService @Inject constructor(private val addressRepository: AddressRepository) {


    fun getAddress(email: String): Flow<List<AddressModel>> {
        return addressRepository.getAddress(email).map { items ->
            items.map {
                AddressModel(
                    id = it.id,
                    name = it.name,
                    address = it.address,
                    city = it.city ?: "",
                    codPostal = it.codPostal ?: "",
                    email = email
                )
            }
        }
    }

    suspend fun addAddress(addressModel: AddressModel){
        addressRepository.addAddress(addressModel.toAddressEntity())
    }

    suspend fun updateAddress(id:Int, name: String, address: String, city: String, codPostal: String){
        addressRepository.updateAddress(id, name, address, city, codPostal)
    }

    suspend fun deleteAddress(id: Int){
        addressRepository.deleteAddress(id)
    }
}