package es.fjmarlop.pizzettApp.dataBase.Remote.apiServices

import es.fjmarlop.pizzettApp.dataBase.Remote.models.EmpleadoModel
import es.fjmarlop.pizzettApp.dataBase.Remote.responses.EmpleadoResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path


/**
 * Interfaz que define las operaciones de la API relacionadas con los empleados.
 */
interface EmpleadoApi {

    /**
     * Comprueba la existencia de un empleado en la base de datos mediante su dirección de correo electrónico.
     *
     * @param email Dirección de correo electrónico del empleado a verificar.
     * @return Retorna un entero que indica el resultado de la comprobación.
     */
    @GET("/pizzettApp/empleado/{email}")
    suspend fun comprobarEmpleado(@Path("email") email: String): Int

    /**
     * Obtiene la lista de todos los empleados desde la API.
     *
     * @param authHeader Encabezado de autorización para autenticar la solicitud.
     * @return Lista de objetos [EmpleadoResponse] que representan a todos los empleados.
     */
    @GET("/pizzettApp/empleados")
    suspend fun getAllEmpleados(@Header("Authorization") authHeader: String): List<EmpleadoResponse>

    /**
     * Inserta un nuevo empleado en la base de datos mediante la API.
     *
     * @param authHeader Encabezado de autorización para autenticar la solicitud.
     * @param empleado Objeto [EmpleadoModel] que representa al empleado a ser insertado.
     * @return Retorna un entero que indica el resultado de la inserción.
     */
    @POST("/pizzettApp/empleado")
    suspend fun insertarEmpleado(@Header("Authorization") authHeader: String, @Body empleado: EmpleadoModel): Int

    /**
     * Elimina un empleado de la base de datos mediante la API.
     *
     * @param authHeader Encabezado de autorización para autenticar la solicitud.
     * @param id Identificador del empleado a ser eliminado.
     * @return Retorna un entero que indica el resultado de la eliminación.
     */
    @DELETE("/pizzettApp/empleado/{id}")
    suspend fun deleteEmpleado(@Header("Authorization") authHeader: String, @Path("id") id: Int): Int
}
