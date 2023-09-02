package es.fjmarlop.pizzettApp.core.retrofit.dao

import es.fjmarlop.pizzettApp.models.PizzaModel
import retrofit2.http.GET
import retrofit2.http.Header

interface PizzaDao {

    @GET("/pizzas")
    suspend fun getAllPizzas(@Header("Authorization") authHeader: String): List<PizzaModel>

}