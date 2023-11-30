package es.fjmarlop.pizzettApp.dataBase.Remote.models

/**
 * Clase de modelo que representa un producto en una línea de pedido.
 *
 * @property idProducto Identificador único del producto.
 * @property nombre_producto Nombre del producto.
 * @property categoria Categoría a la que pertenece el producto.
 * @property tamano Tamaño del producto.
 * @property pvp Precio de venta al público del producto.
 */
data class ProductoLineaPedidoModel(
    val idProducto: Int,
    val nombre_producto: String,
    val categoria: String,
    val tamano: String,
    val pvp: Double
)






