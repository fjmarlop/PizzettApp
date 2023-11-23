package es.fjmarlop.pizzettApp.vistas.cliente.historial.data

import android.util.Log
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.dataBase.Remote.apiServices.PedidoApi
import es.fjmarlop.pizzettApp.dataBase.Remote.models.PedidoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HistoricoRepository @Inject constructor(
    private val pedidoApi: PedidoApi,
    private val utils: Utils
) {

    suspend fun obtnerPedidos(email: String): List<PedidoModel> {
        val token = withContext(Dispatchers.IO) { utils.getToken() }
        runCatching { pedidoApi.obtenerPedidos("Bearer $token", email) }
            .onSuccess { return it.map { item -> item.toModel()  } }
            .onFailure { Log.i("PizzettApp Info", "Error al obtener los pedidos: ${it.message}") }
        return emptyList()
    }
}