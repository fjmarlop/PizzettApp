package es.fjmarlop.pizzettApp.core.retrofit.dao

import es.fjmarlop.pizzettApp.models.databaseModels.ProductoModel
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
interface ProductoDao {

    @GET("/pizzettApp/{cat}")
    suspend fun getProductosPorCategoria(@Header("Authorization") authHeader: String, @Path("cat") cat: String): List<ProductoModel>
}