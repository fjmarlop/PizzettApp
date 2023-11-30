package es.fjmarlop.pizzettApp.dataBase.Remote.responses

import com.google.gson.annotations.SerializedName
import es.fjmarlop.pizzettApp.dataBase.Remote.models.ProductoLineaPedidoModel

/**
 * Clase de modelo de respuesta que representa un producto en una línea de pedido recibido desde un servicio web.
 *
 * @property id Identificador único de la respuesta de la línea de pedido.
 * @property idProducto Identificador único del producto asociado a la línea de pedido.
 * @property nombre_producto Nombre del producto.
 * @property categoria Categoría a la que pertenece el producto.
 * @property tamano Tamaño del producto.
 * @property pvp Precio de venta al público del producto.
 */
data class ProductoLineaPedidoResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("idProducto") val idProducto: Int,
    @SerializedName("nombre_producto") val nombre_producto: String,
    @SerializedName("categoria") val categoria: String,
    @SerializedName("tamano") val tamano: String,
    @SerializedName("pvp") val pvp: Double
) {
    /**
     * Convierte la respuesta de producto en línea de pedido a un modelo de producto en línea de pedido [ProductoLineaPedidoModel].
     *
     * @return Objeto [ProductoLineaPedidoModel] creado a partir de la respuesta de producto en línea de pedido.
     */
    fun toModel(): ProductoLineaPedidoModel {
        return ProductoLineaPedidoModel(
            idProducto = idProducto,
            nombre_producto = nombre_producto,
            categoria = categoria,
            tamano = tamano,
            pvp = pvp
        )
    }
}


