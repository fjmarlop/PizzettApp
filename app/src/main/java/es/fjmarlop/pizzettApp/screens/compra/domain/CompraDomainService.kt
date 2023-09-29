package es.fjmarlop.pizzettApp.screens.compra.domain

import es.fjmarlop.pizzettApp.core.roomDB.entities.toAddressModel
import es.fjmarlop.pizzettApp.core.roomDB.models.AddressModel
import es.fjmarlop.pizzettApp.screens.compra.data.CompraRespository
import javax.inject.Inject

class CompraDomainService @Inject constructor(private val compraRespository: CompraRespository) {


    suspend fun getListAddres(email: String): List<AddressModel> {
        val list = compraRespository.getListAddress(email)
        return if (list.isNotEmpty()) {
            list.map { it.toAddressModel() }
        } else {
            emptyList()
        }
    }
}