package es.fjmarlop.pizzettApp.dataBase.Remote.models

data class PedidoModel (

    val nombreCliente: String,
    val emailCliente: String,
    val telefonoCliente: String,
    val direccionEnvio: String,
    val fechaCreacion: String,
    val fechaPedido: String,
    val total: Double,
    val estado: String,
    val tipoEntrega: String,
    val lineasPedido: List<LineaPedidoModel>,
    )

