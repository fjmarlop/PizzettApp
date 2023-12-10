package es.fjmarlop.pizzettApp.dataBase.Remote.responses

import com.google.gson.annotations.SerializedName
import es.fjmarlop.pizzettApp.dataBase.Remote.models.CategoriaModel
import es.fjmarlop.pizzettApp.dataBase.Remote.models.IngredientsModel
import es.fjmarlop.pizzettApp.dataBase.Remote.models.ProductoModel
import es.fjmarlop.pizzettApp.dataBase.Remote.models.TamaniosModel

/**
 * Clase de modelo de respuesta que representa un producto recibido desde un servicio web.
 *
 * @property id_producto Identificador único del producto.
 * @property nombre_producto Nombre del producto.
 * @property imagen_producto URL o ruta de la imagen del producto.
 * @property descripcion Descripción del producto.
 * @property categoria Conjunto de respuestas [CategoriaResponse] que representan las categorías a las que pertenece el producto.
 * @property ingredients Conjunto de respuestas [IngredientsResponse] que representan los ingredientes del producto.
 * @property tamanios Conjunto de respuestas [TamaniosResponse] que representan los tamaños disponibles del producto.
 */
data class ProductoResponse(
    @SerializedName("id_producto") val id_producto: Int,
    @SerializedName("nombre_producto") val nombre_producto: String,
    @SerializedName("imagen_producto") val imagen_producto: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("categoria") val categoria: Set<CategoriaResponse>,
    @SerializedName("ingredients") val ingredients: Set<IngredientsResponse>,
    @SerializedName("tamanios") val tamanios: Set<TamaniosResponse>
) {
    /**
     * Convierte la respuesta de producto a un modelo de producto [ProductoModel].
     *
     * @return Objeto [ProductoModel] creado a partir de la respuesta de producto.
     */
    fun toModel(): ProductoModel {
        return ProductoModel(
            id_producto = id_producto,
            nombre_producto = nombre_producto,
            imagen_producto = imagen_producto,
            descripcion = descripcion,
            categoria = categoria.map { CategoriaModel(id_categoria = it.id_categoria, nombre_categoria = it.nombre_categoria) }.toSet(),
            ingredients = ingredients.map { IngredientsModel(
                ingredientName = it.ingredientName,
                id = it.id,
            ) }.toSet(),
            tamanios = tamanios.map { TamaniosModel(id = it.id, tamano = it.tamano, pvp = it.pvp) }.toSet()
        )
    }
}

