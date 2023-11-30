package es.fjmarlop.pizzettApp.dataBase.Remote.responses

import com.google.gson.annotations.SerializedName
import es.fjmarlop.pizzettApp.dataBase.Remote.models.IngredientsModel

/**
 * Clase de modelo de respuesta que representa un ingrediente recibido desde un servicio web.
 *
 * @property ingredientName Nombre del ingrediente.
 * @property id Identificador único del ingrediente.
 */
data class IngredientsResponse(
    @SerializedName("ingredientName") val ingredientName: String,
    @SerializedName("id") val id: Int
) {

    /**
     * Convierte la respuesta de ingrediente a un modelo de ingrediente [IngredientsModel].
     *
     * @return Objeto [IngredientsModel] creado a partir de la respuesta de ingrediente.
     */
    fun toModel(): IngredientsModel {
        return IngredientsModel(id = this.id, ingredientName = this.ingredientName)
    }
}

