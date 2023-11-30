package es.fjmarlop.pizzettApp.dataBase.Remote.models


/**
 * Clase de modelo que representa una línea de pedido.
 *
 * @property idLineaPedidoModel Identificador único de la línea de pedido.
 * @property producto Modelo que representa el producto asociado a la línea de pedido.
 * @property cantidad Cantidad de productos en la línea de pedido.
 */
data class LineaPedidoModel(
    val idLineaPedidoModel: Int,
    val producto: ProductoLineaPedidoModel,
    val cantidad: Int
)
