package es.fjmarlop.pizzettApp.core.retrofit.dao

import es.fjmarlop.pizzettApp.models.PizzaModel
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * @author Fco Javier Marmolejo López
 *
 * Controlador para las peticiones Http relacionadas con la clase PizzaModel
 *
 * La anotación @Header("Authorization") nos va a permitir mandar el token de
 * acceso de Firebase para que el servidor compruebe que el usuario está autenticado
 * en la aplicación.
 *
 **/

interface PizzaDao {

    @GET("/pizzas")
    suspend fun getAllPizzas(@Header("Authorization") authHeader: String): List<PizzaModel>

}