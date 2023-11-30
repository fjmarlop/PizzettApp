package es.fjmarlop.pizzettApp.dataBase.Remote.responses

import com.google.gson.annotations.SerializedName
import es.fjmarlop.pizzettApp.dataBase.Remote.models.TamaniosModel

/**
 * Clase de modelo de respuesta que representa un tamaño de producto recibido desde un servicio web.
 *
 * @property id Identificador único del tamaño.
 * @property tamano Nombre o descripción del tamaño.
 * @property pvp Precio de venta al público del tamaño.
 */
data class TamaniosResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("tamano") val tamano: String,
    @SerializedName("pvp") val pvp: Double
) {

    /**
     * Convierte la respuesta de tamaño a un modelo de tamaño [TamaniosModel].
     *
     * @return Objeto [TamaniosModel] creado a partir de la respuesta de tamaño.
     */
    fun toModel(): TamaniosModel {
        return TamaniosModel(id = this.id, tamano = this.tamano, pvp = this.pvp)
    }
}
