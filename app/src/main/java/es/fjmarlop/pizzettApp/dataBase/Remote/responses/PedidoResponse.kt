package es.fjmarlop.pizzettApp.dataBase.Remote.responses

import com.google.gson.annotations.SerializedName
import es.fjmarlop.pizzettApp.dataBase.Remote.models.PedidoModel

data class PedidoResponse(
    @SerializedName("id") val idPedido: Int,
    @SerializedName("nombreCliente") val nombreCliente: String,
    @SerializedName("emailCliente") val emailCliente: String,
    @SerializedName("telefonoCliente") val telefonoCliente: String,
    @SerializedName("direccionEnvio") val direccionEnvio: String,
    @SerializedName("fechaCreacion") val fechaCreacion: String,
    @SerializedName("fechaPedido") val fechaPedido: String,
    @SerializedName("total") val total: Double,
    @SerializedName("estado") val estado: String,
    @SerializedName("tipoEntrega") val tipoEntrega: String,
    @SerializedName("lineasPedido") val lineasPedido: List<LineaPedidoResponse>,
){
    fun toModel():PedidoModel{
        return PedidoModel(

            nombreCliente = nombreCliente,
            emailCliente = emailCliente,
            telefonoCliente = telefonoCliente,
            direccionEnvio = direccionEnvio,
            fechaCreacion = fechaCreacion,
            fechaPedido = fechaPedido,
            total = total,
            estado = estado,
            tipoEntrega = tipoEntrega,
            lineasPedido = lineasPedido.map { it.toModel() }.toList()
        )
    }

}

