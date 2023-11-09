package es.fjmarlop.pizzettApp.dataBase.Remote.retrofit.apiServices

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path


interface EmpleadoApi {

    @GET("/pizzettApp/empleado/{email}")
    suspend fun comprobarEmpleado(@Header("Authorization") authHeader: String, @Path("email") email: String):Int


}