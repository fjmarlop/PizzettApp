package es.fjmarlop.pizzettApp.dataBase.Remote.responses

import com.google.gson.annotations.SerializedName
import es.fjmarlop.pizzettApp.dataBase.Remote.models.CategoriaModel

/**
 * Clase de modelo de respuesta que representa una categoría recibida desde un servicio web.
 *
 * @property id_categoria Identificador único de la categoría.
 * @property nombre_categoria Nombre de la categoría.
 */
data class CategoriaResponse(
    @SerializedName("id_categoria") val id_categoria: Int,
    @SerializedName("nombre_categoria") val nombre_categoria: String
) {
    /**
     * Convierte la respuesta de categoría a un modelo de categoría [CategoriaModel].
     *
     * @return Objeto [CategoriaModel] creado a partir de la respuesta de categoría.
     */
    fun toCategoriaModel(): CategoriaModel {
        return CategoriaModel(id_categoria = id_categoria, nombre_categoria = nombre_categoria)
    }
}
