package es.fjmarlop.pizzettApp.vistas.cliente.compra.domain

import es.fjmarlop.pizzettApp.dataBase.local.entities.toAddressModel
import es.fjmarlop.pizzettApp.dataBase.local.models.AddressModel
import es.fjmarlop.pizzettApp.vistas.cliente.compra.data.CompraRespository
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