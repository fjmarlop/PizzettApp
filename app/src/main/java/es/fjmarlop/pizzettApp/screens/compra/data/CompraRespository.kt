package es.fjmarlop.pizzettApp.screens.compra.data

import es.fjmarlop.pizzettApp.core.roomDB.dao.AddressDao
import es.fjmarlop.pizzettApp.core.roomDB.entities.AddressEntity
import javax.inject.Inject

class CompraRespository @Inject constructor(private val addressDao: AddressDao) {

    suspend fun getListAddress(email: String):List<AddressEntity>{
        return addressDao.getListAddress(email)
    }
}