package es.fjmarlop.pizzettApp.core.retrofit.apiServices

import es.fjmarlop.pizzettApp.core.retrofit.models.ProductoModel
import es.fjmarlop.pizzettApp.core.retrofit.responses.ProductoResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 * @author Fco Javier Marmolejo López
 *
 * Controlador para las peticiones Http relacionadas con la clase ProductoModel
 *
 * La anotación @Header("Authorization") nos va a permitir mandar el token de
 * acceso de Firebase para que el servidor compruebe que el usuario está autenticado
 * en la aplicación.
 *
 **/
interface ProductoApi {

    @GET("/pizzettApp/{cat}")
    suspend fun getProductosPorCategoria(@Header("Authorization") authHeader: String, @Path("cat") cat: String): List<ProductoModel>

    @GET("/pizzettApp/productos")
    suspend fun getAllProducts():List<ProductoResponse>

    @GET("/pizzettApp/recomendados")
    suspend fun getProductosParaRecomendados(@Header("Authorization") authHeader: String):List<ProductoResponse>

}