package es.fjmarlop.pizzettApp.vistas.cliente.compra.data

import android.util.Log
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.dataBase.Remote.apiServices.PedidoApi
import es.fjmarlop.pizzettApp.dataBase.Remote.models.PedidoModel
import es.fjmarlop.pizzettApp.dataBase.local.dao.AddressDao
import es.fjmarlop.pizzettApp.dataBase.local.entities.AddressEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repositorio para gestionar la compra, interactuando con la base de datos y la API.
 *
 * @param addressDao Objeto de acceso a datos para las direcciones.
 * @param pedidoApi Interfaz para interactuar con la API de pedidos.
 * @param utils Utilidades compartidas.
 */
class CompraRespository @Inject constructor(
    private val addressDao: AddressDao,
    private val pedidoApi: PedidoApi,
    private val utils: Utils
) {

    /**
     * Obtiene la lista de direcciones asociadas a un usuario.
     *
     * @param email Correo electrónico del usuario.
     * @return Lista de entidades de direcciones.
     */
    suspend fun getListAddress(email: String):List<AddressEntity>{
        return addressDao.getListAddress(email)
    }

    /**
     * Finaliza un pedido realizando la inserción en la API.
     *
     * @param pedido Modelo de datos del pedido.
     * @return Código de estado de la operación (1 si tiene éxito, 0 si falla).
     */
    suspend fun finalizarPedido(pedido: PedidoModel): Int {
        val token = withContext(Dispatchers.IO) { utils.getToken() }
        runCatching { pedidoApi.insertarPedido( "Bearer $token", pedido) }
            .onSuccess { return 1 }
            .onFailure { Log.i("PizzettApp Info", "Error: ${it.message}") }
        return 0
    }
}

