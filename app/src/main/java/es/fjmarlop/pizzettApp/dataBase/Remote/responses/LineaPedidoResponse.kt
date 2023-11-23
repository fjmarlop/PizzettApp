package es.fjmarlop.pizzettApp.dataBase.Remote.responses

import com.google.gson.annotations.SerializedName
import es.fjmarlop.pizzettApp.dataBase.Remote.models.LineaPedidoModel

data class LineaPedidoResponse(

    @SerializedName("idLinea") val idLinea: Int,
    @SerializedName("cantidad") val cantidad: Int,
    @SerializedName("producto") val producto: ProductoLineaPedidoResponse
){

    fun toModel(): LineaPedidoModel {
        return LineaPedidoModel(
            idLineaPedidoModel = idLinea,
            cantidad = cantidad,
            producto = producto.toModel()
        )
    }
}

