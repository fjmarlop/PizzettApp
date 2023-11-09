package es.fjmarlop.pizzettApp.dataBase.Remote.apiServices

import retrofit2.http.GET
import retrofit2.http.Path


interface EmpleadoApi {

    @GET("/pizzettApp/empleado/{email}")
    suspend fun comprobarEmpleado(@Path("email") email: String):Int


}