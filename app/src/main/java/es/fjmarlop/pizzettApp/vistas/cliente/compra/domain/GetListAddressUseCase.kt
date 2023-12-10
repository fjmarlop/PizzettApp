package es.fjmarlop.pizzettApp.vistas.cliente.compra.domain

import es.fjmarlop.pizzettApp.dataBase.local.entities.toAddressModel
import es.fjmarlop.pizzettApp.dataBase.local.models.AddressModel
import es.fjmarlop.pizzettApp.vistas.cliente.compra.data.CompraRespository
import javax.inject.Inject

class GetListAddressUseCase @Inject constructor(private val compraRespository: CompraRespository) {

    /**
     * Obtiene la lista de direcciones asociadas a un usuario.
     *
     * @param email Correo electrónico del usuario.
     * @return Lista de modelos de direcciones.
     */
    suspend operator fun invoke(email: String): List<AddressModel> {
        val list = compraRespository.getListAddress(email)
        return if (list.isNotEmpty()) {
            list.map { it.toAddressModel() }
        } else {
            emptyList()
        }
    }

}