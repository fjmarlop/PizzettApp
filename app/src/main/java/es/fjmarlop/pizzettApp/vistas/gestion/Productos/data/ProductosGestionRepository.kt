package es.fjmarlop.pizzettApp.vistas.gestion.Productos.data

import android.util.Log
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.dataBase.Remote.apiServices.ProductoApi
import es.fjmarlop.pizzettApp.dataBase.Remote.models.IngredientsModel
import es.fjmarlop.pizzettApp.dataBase.Remote.models.ProductoModel
import es.fjmarlop.pizzettApp.dataBase.Remote.models.TamaniosModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repositorio para gestionar operaciones relacionadas con la gestión de productos.
 *
 * Este repositorio interactúa con la API de productos [ProductoApi] para realizar operaciones
 * como obtener todos los productos, obtener ingredientes y obtener tamaños disponibles.
 *
 * @property productoApi Instancia de [ProductoApi] utilizada para realizar operaciones relacionadas con los productos.
 * @property utils Instancia de [Utils] utilizada para funciones de utilidad.
 */
class ProductosGestionRepository @Inject constructor(
    private val productoApi: ProductoApi,
    private val utils: Utils) {

    /**
     * Obtiene la lista de todos los productos desde la API.
     *
     * @return Lista de [ProductoModel] que representa a todos los productos.
     */
    suspend fun getProductos(): List<ProductoModel> {
        val token = withContext(Dispatchers.IO){ utils.getToken() }
       runCatching { productoApi.getTodosLosProductos("Bearer $token") }
           .onSuccess { return it.map{ item -> item.toModel()} }
           .onFailure { Log.i("PizzettApp info", "Ha habido un error al obtener los productos") }
       return emptyList()
    }

    /**
     * Obtiene la lista de todos los ingredientes desde la API.
     *
     * @return Lista de [IngredientsModel] que representa a todos los ingredientes.
     */
    suspend fun getIngredientes(): List<IngredientsModel> {
        val token = withContext(Dispatchers.IO){ utils.getToken() }
        runCatching { productoApi.getIngredientes("Bearer $token") }
            .onSuccess { return it.map { item -> item.toModel() } }
            .onFailure { Log.i("PizzettApp info", "Ha habido un error al obtener los ingredientes") }
        return emptyList()
    }

    /**
     * Obtiene la lista de todos los tamaños disponibles desde la API.
     *
     * @return Lista de [TamaniosModel] que representa a todos los tamaños disponibles.
     */
    suspend fun getTamanos(): List<TamaniosModel> {
        val token = withContext(Dispatchers.IO){ utils.getToken() }
        runCatching { productoApi.getTamanos("Bearer $token") }
            .onSuccess { return it.map { item -> item.toModel() } }
            .onFailure { Log.i("PizzettApp info", "Ha habido un error al obtener los tamaños") }
        return emptyList()
    }

    /**
     * Inserta un producto en la base de datos.
     * @param producto objeto [ProductoModel] que representa el nuevo producto.
     * @return [Int] con el valor que indica el estado de la inserción.
     */
    suspend fun addProducto(producto: ProductoModel):Int{
        val token = withContext(Dispatchers.IO){ utils.getToken() }
        runCatching { productoApi.addProducto("Bearer $token", producto) }
            .onSuccess { return 1 }
            .onFailure { Log.i("PizzettApp info", "Ha habido un error al añadir el producto") }
        return 0
    }

    /**
     * Elimina un producto de la base de datos.
     * @param id ID del producto a borrar.
     * @return [Int] con el valor que indica el estado de la inserción.
     */
    suspend fun borrarProducto(id: Int):Int{
        val token = withContext(Dispatchers.IO){ utils.getToken() }
        runCatching { productoApi.borrarProducto("Bearer $token", id) }
            .onSuccess { return  1 }
            .onFailure { Log.i("PizzettApp info", "Ha habido un error al borrar el producto") }
        return 0
    }
}