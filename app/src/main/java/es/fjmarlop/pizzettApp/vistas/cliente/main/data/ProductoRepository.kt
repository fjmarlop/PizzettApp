package es.fjmarlop.pizzettApp.vistas.cliente.main.data

import android.util.Log
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.dataBase.Remote.apiServices.ProductoApi
import es.fjmarlop.pizzettApp.dataBase.Remote.models.ProductoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repositorio para la gestión de productos.
 *
 * Este repositorio interactúa con la API de productos [ProductoApi] y la clase de utilidades [Utils]
 * para obtener información sobre productos por categoría y productos recomendados.
 *
 * @property productoApi Instancia de [ProductoApi] utilizada para realizar operaciones relacionadas con los productos.
 * @property utils Instancia de [Utils] utilizada para funciones de utilidad, como la obtención del token.
 */
class ProductoRepository @Inject constructor(
    private val productoApi: ProductoApi,
    private val utils: Utils,
) {

    /**
     * Obtiene la lista de productos por categoría.
     *
     * @param cat Categoría de productos a consultar.
     * @return Lista de [ProductoModel] que representa los productos de la categoría especificada.
     */
    suspend fun getProductosPorCategoria(cat: String): List<ProductoModel>? {
        val token = withContext(Dispatchers.IO) { utils.getToken() }
        runCatching { productoApi.getProductosPorCategoria("Bearer $token", cat) }
            .onSuccess { return it }
            .onFailure { Log.i("PizzettApp Info", "Error: ${it.message}") }
        return null
    }

    /**
     * Obtiene la lista de productos recomendados.
     *
     * @return Lista de [ProductoModel] que representa los productos recomendados.
     */
    suspend fun getProductosParaRecomendados(): List<ProductoModel>?{
        val token = withContext(Dispatchers.IO) { utils.getToken() }
        runCatching { productoApi.getProductosParaRecomendados("Bearer $token") }
            .onSuccess { return it.map { item -> item.toModel() } }
            .onFailure { Log.i("PizzettApp Info", "Error: ${it.message}") }
        return null
    }
}