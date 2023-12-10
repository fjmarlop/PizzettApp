package es.fjmarlop.pizzettApp.dataBase.Remote.apiServices

import es.fjmarlop.pizzettApp.dataBase.Remote.models.ProductoModel
import es.fjmarlop.pizzettApp.dataBase.Remote.responses.IngredientsResponse
import es.fjmarlop.pizzettApp.dataBase.Remote.responses.ProductoResponse
import es.fjmarlop.pizzettApp.dataBase.Remote.responses.TamaniosResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path


/**
 * Interfaz que define las operaciones de la API relacionadas con los productos.
 */
interface ProductoApi {

    /**
     * Obtiene la lista de productos por categoría desde la API.
     *
     * @param authHeader Encabezado de autorización para autenticar la solicitud.
     * @param cat Categoría de productos a obtener.
     * @return Lista de objetos [ProductoModel] que representan los productos de la categoría especificada.
     */
    @GET("/pizzettApp/{cat}")
    suspend fun getProductosPorCategoria(@Header("Authorization") authHeader: String, @Path("cat") cat: String): List<ProductoModel>

    /**
     * Obtiene la lista de productos recomendados desde la API.
     *
     * @param authHeader Encabezado de autorización para autenticar la solicitud.
     * @return Lista de objetos [ProductoResponse] que representan los productos recomendados.
     */
    @GET("/pizzettApp/recomendados")
    suspend fun getProductosParaRecomendados(@Header("Authorization") authHeader: String): List<ProductoResponse>

    /**
     * Obtiene la lista de todos los productos desde la API.
     *
     * @param authHeader Encabezado de autorización para autenticar la solicitud.
     * @return Lista de objetos [ProductoResponse] que representan todos los productos disponibles.
     */
    @GET("/pizzettApp/productos")
    suspend fun getTodosLosProductos(@Header("Authorization") authHeader: String): List<ProductoResponse>

    // Operaciones relacionadas con los ingredientes y tamaños

    /**
     * Obtiene la lista de ingredientes desde la API.
     *
     * @param authHeader Encabezado de autorización para autenticar la solicitud.
     * @return Lista de objetos [IngredientsResponse] que representan los ingredientes disponibles.
     */
    @GET("/pizzettApp/ingredientes")
    suspend fun getIngredientes(@Header("Authorization") authHeader: String): List<IngredientsResponse>

    /**
     * Obtiene la lista de tamaños de productos desde la API.
     *
     * @param authHeader Encabezado de autorización para autenticar la solicitud.
     * @return Lista de objetos [TamaniosResponse] que representan los tamaños disponibles.
     */
    @GET("/pizzettApp/tamanos")
    suspend fun getTamanos(@Header("Authorization") authHeader: String): List<TamaniosResponse>

    /**
     * Inserta un nuevo producto en la base de datos.
     * @param authHeader Encabezado de autorización para autenticar la solicitud.
     * @param producto objeto [ProductoModel] que representa el nuevo producto.
     * @return Objeto [ProductoResponse] con la respuesta de la API.
     */
    @POST("/pizzettApp/addProducto")
    suspend fun addProducto(@Header("Authorization") authHeader: String, @Body producto :ProductoModel): Response<ProductoResponse>

    /**
     * Elimina un producto de la base de datos.
     * @param authHeader Encabezado de autorización para autenticar la solicitud.
     * @param id ID del producto a borrar.
     * @return Objeto [ProductoResponse] con la respuesta de la API.
     */
    @DELETE("/pizzettApp/borrarProducto/{id}")
    suspend fun borrarProducto(@Header("Authorization") authHeader: String, @Path("id") id: Int): Response<ProductoResponse>
}
