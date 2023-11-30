package es.fjmarlop.pizzettApp.dataBase.Remote.responses

import com.google.gson.annotations.SerializedName
import es.fjmarlop.pizzettApp.dataBase.Remote.models.PedidoModel


/**
 * Clase de modelo de respuesta que representa un pedido recibido desde un servicio web.
 *
 * @property idPedido Identificador único del pedido.
 * @property nombreCliente Nombre del cliente que realizó el pedido.
 * @property emailCliente Dirección de correo electrónico del cliente.
 * @property telefonoCliente Número de teléfono del cliente.
 * @property direccionEnvio Dirección de envío del pedido.
 * @property fechaCreacion Fecha de creación del pedido.
 * @property fechaPedido Fecha del pedido.
 * @property total Monto total del pedido.
 * @property estado Estado actual del pedido.
 * @property tipoEntrega Tipo de entrega del pedido.
 * @property lineasPedido Lista de modelos que representan las líneas de productos en el pedido.
 */
data class PedidoResponse(
    @SerializedName("idPedido") val idPedido: Int,
    @SerializedName("nombreCliente") val nombreCliente: String,
    @SerializedName("emailCliente") val emailCliente: String,
    @SerializedName("telefonoCliente") val telefonoCliente: String,
    @SerializedName("direccionEnvio") val direccionEnvio: String,
    @SerializedName("fechaCreacion") val fechaCreacion: String,
    @SerializedName("fechaPedido") val fechaPedido: String,
    @SerializedName("total") val total: Double,
    @SerializedName("estado") val estado: String,
    @SerializedName("tipoEntrega") val tipoEntrega: String,
    @SerializedName("lineasPedido") val lineasPedido: List<LineaPedidoResponse>
) {
    /**
     * Convierte la respuesta de pedido a un modelo de pedido [PedidoModel].
     *
     * @return Objeto [PedidoModel] creado a partir de la respuesta de pedido.
     */
    fun toModel(): PedidoModel {
        return PedidoModel(
            id = idPedido,
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
