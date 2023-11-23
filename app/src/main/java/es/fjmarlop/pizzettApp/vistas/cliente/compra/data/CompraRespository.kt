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

class CompraRespository @Inject constructor(
    private val addressDao: AddressDao,
    private val pedidoApi: PedidoApi,
    private val utils: Utils
) {

    suspend fun getListAddress(email: String):List<AddressEntity>{
        return addressDao.getListAddress(email)
    }

    suspend fun finalizarPedido(pedido: PedidoModel): Int {
        val token = withContext(Dispatchers.IO) { utils.getToken() }
        runCatching { pedidoApi.insertarPedido( "Bearer $token", pedido) }
            .onSuccess { return 1 }
            .onFailure { Log.i("PizzettApp Info", "Error: ${it.message}") }
        return 0
    }
}

