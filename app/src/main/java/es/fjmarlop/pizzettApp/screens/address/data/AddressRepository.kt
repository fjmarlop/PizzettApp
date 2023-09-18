package es.fjmarlop.pizzettApp.screens.address.data

import es.fjmarlop.pizzettApp.core.roomDB.dao.AddressDao
import es.fjmarlop.pizzettApp.core.roomDB.entities.AddressEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressRepository @Inject constructor(private val addressDao: AddressDao){


    fun getAddress(email: String): Flow<List<AddressEntity>>{
        return addressDao.getAddress(email)
    }

    suspend fun addAddress(addressEntity: AddressEntity){
        addressDao.addAddress(addressEntity)
    }

   suspend fun updateAddress(id: Int, name: String, address: String, city: String, codPostal: String){
       addressDao.updateAddress(id, name, address, city, codPostal)
   }

    suspend fun deleteAddress(id: Int){
        addressDao.deleteAddress(id)
    }

}