package es.fjmarlop.pizzettApp.dataBase.Remote.apiServices

import es.fjmarlop.pizzettApp.dataBase.Remote.models.PedidoModel
import es.fjmarlop.pizzettApp.dataBase.Remote.responses.PedidoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**import java.util.concurrent.
 * Interfaz que define las operaciones de la API relacionadas con los pedidos.
 */
interface PedidoApi {

   /**
    * Inserta un nuevo pedido en la base de datos mediante la API.
    *
    * @param authHeader Encabezado de autorización para autenticar la solicitud.
    * @param pedidoModel Objeto [PedidoModel] que representa el pedido a ser insertado.
    * @return Objeto [Response] que contiene la respuesta de la inserción y un objeto [PedidoResponse].
    */
   @POST("/pizzettApp/pedido")
   suspend fun insertarPedido(@Header("Authorization") authHeader: String, @Body pedidoModel: PedidoModel): Response<PedidoResponse>

   /**
    * Obtiene la lista de pedidos asociados a un cliente mediante su dirección de correo electrónico.
    *
    * @param authHeader Encabezado de autorización para autenticar la solicitud.
    * @param email Dirección de correo electrónico del cliente para obtener sus pedidos.
    * @return Lista de objetos [PedidoResponse] que representan los pedidos del cliente.
    */
   @GET("/pizzettApp/pedidos/{email}")
   suspend fun obtenerPedidos(@Header("Authorization") authHeader: String, @Path("email") email: String): List<PedidoResponse>

   /**
    * Obtiene la lista de todos los pedidos almacenados en la base de datos mediante la API.
    *
    * @param authHeader Encabezado de autorización para autenticar la solicitud.
    * @return Lista de objetos [PedidoResponse] que representan todos los pedidos.
    */
   @GET("/pizzettApp/allpedidos")
   suspend fun obtenerTodosLosPedidos(@Header("Authorization") authHeader: String): List<PedidoResponse>

   /**
    * Actualiza el estado de un pedido en la base de datos mediante la API.
    *
    * @param authHeader Encabezado de autorización para autenticar la solicitud.
    * @param id Identificador del pedido a ser actualizado.
    * @param estado Nuevo estado que se asignará al pedido.
    * @return Objeto [Response] que contiene la respuesta de la actualización y un objeto [PedidoResponse].
    */

   @PUT("/pizzettApp/actualizarEstado/{id}/{estado}")
   suspend fun actualizarEstado(@Header("Authorization") authHeader: String, @Path("id") id: Int, @Path("estado") estado: String): Response<PedidoResponse>
}
