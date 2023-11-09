package es.fjmarlop.pizzettApp.dataBase.Remote.responses

import com.google.gson.annotations.SerializedName
import es.fjmarlop.pizzettApp.dataBase.Remote.models.CategoriaModel
import es.fjmarlop.pizzettApp.dataBase.Remote.models.IngredientsModel
import es.fjmarlop.pizzettApp.dataBase.Remote.models.ProductoModel
import es.fjmarlop.pizzettApp.dataBase.Remote.models.TamaniosModel

data class ProductoResponse(

    @SerializedName("id_producto") val id_producto: Int,

    @SerializedName("nombre_producto") val nombre_producto: String,

    @SerializedName("imagen_producto") val imagen_producto: String,

    @SerializedName("descripcion") val descripcion: String,

    @SerializedName("categoria") val categoria: Set<CategoriaResponse>,

    @SerializedName("ingredients") val ingredients: Set<IngredientsResponse>,

    @SerializedName("tamanios") val tamanios: Set<TamaniosResponse>
)
{
    fun toModel(): ProductoModel {
        return ProductoModel(
            id_producto = id_producto,
            nombre_producto = nombre_producto,
            imagen_producto = imagen_producto,
            descripcion = descripcion,
            categoria = categoria.map { CategoriaModel(id_categoria = it.id_categoria, nombre_categoria = it.nombre_categoria) }.toSet(),
            ingredients = ingredients.map { IngredientsModel(id = it.id, ingredientName = it.ingredientName) }.toSet(),
            tamanios = tamanios.map { TamaniosModel(id = it.id, tamano = it.tamano, pvp = it.pvp) }.toSet()

        )
    }

}
