package es.fjmarlop.pizzettApp.vistas.cliente.compra.data

import es.fjmarlop.pizzettApp.dataBase.local.roomDB.dao.AddressDao
import es.fjmarlop.pizzettApp.dataBase.local.roomDB.entities.AddressEntity
import javax.inject.Inject

class CompraRespository @Inject constructor(private val addressDao: AddressDao) {

    suspend fun getListAddress(email: String):List<AddressEntity>{
        return addressDao.getListAddress(email)
    }
}