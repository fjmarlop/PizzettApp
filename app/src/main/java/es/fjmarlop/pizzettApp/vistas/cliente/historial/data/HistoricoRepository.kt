package es.fjmarlop.pizzettApp.vistas.cliente.historial.data

import android.util.Log
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.dataBase.Remote.apiServices.PedidoApi
import es.fjmarlop.pizzettApp.dataBase.Remote.models.PedidoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * Repositorio para la gestión del histórico de pedidos.
 *
 * Este repositorio interactúa con la API de pedidos [PedidoApi] y la clase de utilidades [Utils]
 * para obtener información sobre los pedidos realizados por un usuario.
 *
 * @property pedidoApi Instancia de [PedidoApi] utilizada para realizar operaciones relacionadas con los pedidos.
 * @property utils Instancia de [Utils] utilizada para funciones de utilidad, como la obtención del token.
 */
class HistoricoRepository @Inject constructor(
    private val pedidoApi: PedidoApi,
    private val utils: Utils
) {

    /**
     * Obtiene la lista de pedidos realizados por un usuario específico.
     *
     * @param email Correo electrónico del usuario cuyos pedidos se van a obtener.
     * @return Lista de [PedidoModel] que representa los pedidos del usuario.
     */
    suspend fun obtnerPedidos(email: String): List<PedidoModel> {
        val token = withContext(Dispatchers.IO) { utils.getToken() }
        runCatching { pedidoApi.obtenerPedidos("Bearer $token", email) }
            .onSuccess { return it.map { item -> item.toModel()  } }
            .onFailure { Log.i("PizzettApp Info", "Error al obtener los pedidos: ${it.message}") }
        return emptyList()
    }
}