package es.fjmarlop.pizzettApp.dataBase.Remote.responses

import com.google.gson.annotations.SerializedName
import es.fjmarlop.pizzettApp.dataBase.Remote.models.ProductoLineaPedidoModel

data class ProductoLineaPedidoResponse(

    @SerializedName("id") val id: Int,
    @SerializedName("idProducto") val idProducto: Int,
    @SerializedName("nombre_producto") val nombre_producto: String,
    @SerializedName("categoria") val categoria: String,
    @SerializedName("tamano") val tamano: String,
    @SerializedName("pvp") val pvp: Double

){
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

