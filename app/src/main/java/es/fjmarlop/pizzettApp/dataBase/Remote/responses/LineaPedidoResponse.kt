package es.fjmarlop.pizzettApp.dataBase.Remote.responses

import com.google.gson.annotations.SerializedName
import es.fjmarlop.pizzettApp.dataBase.Remote.models.LineaPedidoModel

/**
 * Clase de modelo de respuesta que representa una línea de pedido recibida desde un servicio web.
 *
 * @property idLinea Identificador único de la línea de pedido.
 * @property cantidad Cantidad de productos en la línea de pedido.
 * @property producto Modelo de respuesta que representa el producto asociado a la línea de pedido.
 */
data class LineaPedidoResponse(
    @SerializedName("idLinea") val idLinea: Int,
    @SerializedName("cantidad") val cantidad: Int,
    @SerializedName("producto") val producto: ProductoLineaPedidoResponse
) {

    /**
     * Convierte la respuesta de línea de pedido a un modelo de línea de pedido [LineaPedidoModel].
     *
     * @return Objeto [LineaPedidoModel] creado a partir de la respuesta de línea de pedido.
     */
    fun toModel(): LineaPedidoModel {
        return LineaPedidoModel(
            idLineaPedidoModel = idLinea,
            cantidad = cantidad,
            producto = producto.toModel()
        )
    }
}


