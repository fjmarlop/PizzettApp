package es.fjmarlop.pizzettApp.core.retrofit.responses

import com.google.gson.annotations.SerializedName
import es.fjmarlop.pizzettApp.core.retrofit.models.NewProductoModel
import es.fjmarlop.pizzettApp.core.retrofit.models.TamaniosModel

data class ProductoResponse(

    @SerializedName("id_producto") val id_producto: Int,

    @SerializedName("nombre_producto") val nombre_producto: String,

    @SerializedName("imagen_producto") val imagen_producto: String,

    @SerializedName("descripcion") val descripcion: String,

    @SerializedName("categoria") val categoria: Set<CategoriaResponse>,

    @SerializedName("ingredients") val ingredients: Set<IngredientsResponse>,

    @SerializedName("tamanios") val tamanios: Set<TamaniosResponse>
) {
    fun toModel(): NewProductoModel {
        return NewProductoModel(
            id_producto = id_producto,
            nombre_producto = nombre_producto,
            imagen_producto = imagen_producto,
            descripcion = descripcion,
            categoria = categoria.map { it.nombre_categoria }.toString(),
            ingredients = ingredients.map { it.ingredientName },
            tamanios = tamanios.map { TamaniosModel(id = it.id, tamano = it.tamano, pvp = it.pvp) }
        )
    }

}
