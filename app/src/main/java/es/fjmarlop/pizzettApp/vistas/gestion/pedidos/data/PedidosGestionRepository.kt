package es.fjmarlop.pizzettApp.vistas.gestion.pedidos.data

import android.util.Log
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.dataBase.Remote.apiServices.PedidoApi
import es.fjmarlop.pizzettApp.dataBase.Remote.models.PedidoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repositorio para gestionar operaciones relacionadas con la gestión de pedidos.
 *
 * Este repositorio interactúa con la API de pedidos [PedidoApi] para realizar operaciones
 * como obtener todos los pedidos y actualizar el estado de un pedido existente.
 *
 * @property pedidoApi Instancia de [PedidoApi] utilizada para realizar operaciones relacionadas con los pedidos.
 * @property utils Instancia de [Utils] utilizada para funciones de utilidad.
 */
class PedidosGestionRepository @Inject constructor(
    private val pedidoApi: PedidoApi,
    private val utils: Utils
) {

    /**
     * Obtiene la lista de todos los pedidos desde la API.
     *
     * @return Lista de [PedidoModel] que representa a todos los pedidos.
     */
    suspend fun getAllPedidos(): List<PedidoModel> {
        val token = withContext(Dispatchers.IO){ utils.getToken() }
        runCatching { pedidoApi.obtenerTodosLosPedidos("Bearer $token") }
            .onSuccess { return it.map{ item -> item.toModel()} }
            .onFailure { Log.i("PizzettApp info", "Ha habido un error al obtener los pedidos") }
        return emptyList()
    }

    /**
     * Actualiza el estado de un pedido existente utilizando la API.
     *
     * @param id Identificador del pedido a actualizar.
     * @param estado Nuevo estado del pedido.
     * @return `true` si la actualización del estado es exitosa, `false` en caso de error.
     */
    suspend fun actualizarEstado(id: Int, estado: String): Boolean {
        val token = withContext(Dispatchers.IO){ utils.getToken() }
        runCatching {pedidoApi.actualizarEstado("Bearer $token", id, estado) }
            .onSuccess { return true }
            .onFailure { Log.i("PizzettApp info", "Ha habido un error al actualizar el estado del pedido") }
        return false
    }
}