package es.fjmarlop.pizzettApp.vistas.cliente.compra.domain

import es.fjmarlop.pizzettApp.dataBase.Remote.models.PedidoModel
import es.fjmarlop.pizzettApp.dataBase.local.entities.toAddressModel
import es.fjmarlop.pizzettApp.dataBase.local.models.AddressModel
import es.fjmarlop.pizzettApp.vistas.cliente.compra.data.CompraRespository
import javax.inject.Inject

/**
 * Servicio de dominio para gestionar operaciones relacionadas con la compra.
 *
 * @param compraRespository Repositorio para interactuar con la base de datos y la API de compra.
 */
class CompraDomainService @Inject constructor(private val compraRespository: CompraRespository) {

    /**
     * Obtiene la lista de direcciones asociadas a un usuario.
     *
     * @param email Correo electrónico del usuario.
     * @return Lista de modelos de direcciones.
     */
    suspend fun getListAddres(email: String): List<AddressModel> {
        val list = compraRespository.getListAddress(email)
        return if (list.isNotEmpty()) {
            list.map { it.toAddressModel() }
        } else {
            emptyList()
        }
    }

    /**
     * Finaliza un pedido realizando la inserción en la API.
     *
     * @param pedido Modelo de datos del pedido.
     * @return Código de estado de la operación (1 si tiene éxito, 0 si falla).
     */
   suspend fun finalizarPedido(pedido: PedidoModel):Int {
        return compraRespository.finalizarPedido(pedido)
    }

}