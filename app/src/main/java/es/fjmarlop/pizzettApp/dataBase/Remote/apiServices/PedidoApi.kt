package es.fjmarlop.pizzettApp.dataBase.Remote.apiServices

import es.fjmarlop.pizzettApp.dataBase.Remote.models.PedidoModel
import es.fjmarlop.pizzettApp.dataBase.Remote.responses.PedidoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface PedidoApi {

   @POST("/pizzettApp/pedido")
   suspend fun  insertarPedido(@Header("Authorization") authHeader: String, @Body pedidoModel: PedidoModel): Response<PedidoResponse>

   @GET("/pizzettApp/pedidos/{email}")
   suspend fun  obtenerPedidos(@Header("Authorization") authHeader: String, @Path("email") email: String): List<PedidoResponse>
}