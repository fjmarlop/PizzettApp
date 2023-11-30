package es.fjmarlop.pizzettApp.dataBase.Remote.models

/**
 * Clase de modelo que representa un pedido.
 *
 * @property id Identificador único del pedido.
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
data class PedidoModel(
    val id: Int,
    val nombreCliente: String,
    val emailCliente: String,
    val telefonoCliente: String,
    val direccionEnvio: String,
    val fechaCreacion: String,
    val fechaPedido: String,
    val total: Double,
    val estado: String,
    val tipoEntrega: String,
    val lineasPedido: List<LineaPedidoModel>
)


