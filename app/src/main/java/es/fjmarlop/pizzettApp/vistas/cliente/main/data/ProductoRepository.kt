package es.fjmarlop.pizzettApp.vistas.cliente.main.data

import android.util.Log
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.dataBase.Remote.apiServices.ProductoApi
import es.fjmarlop.pizzettApp.dataBase.Remote.models.ProductoModel
import es.fjmarlop.pizzettApp.dataBase.local.dao.ProductDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductoRepository @Inject constructor(
    private val productoApi: ProductoApi,
    private val utils: Utils,
    private val productDao: ProductDao
) {

    suspend fun getProductosPorCategoria(cat: String): List<ProductoModel>? {
        val token = withContext(Dispatchers.IO) { utils.getToken() }
        runCatching { productoApi.getProductosPorCategoria("Bearer $token", cat) }
            .onSuccess { return it }
            .onFailure { Log.i("PizzettApp Info", "Error: ${it.message}") }
        return null
    }

    suspend fun getProductosParaRecomendados(): List<ProductoModel>?{
        val token = withContext(Dispatchers.IO) { utils.getToken() }
        runCatching { productoApi.getProductosParaRecomendados("Bearer $token") }
            .onSuccess { return it.map { item -> item.toModel() } }
            .onFailure { Log.i("PizzettApp Info", "Error: ${it.message}") }
        return null
    }
}