package es.fjmarlop.pizzettApp.dataBase.Remote.models


/**
 * Clase de modelo que representa un tamaño de producto.
 *
 * @property id Identificador único del tamaño (puede ser nulo si el tamaño es nuevo y aún no ha sido asignado un ID).
 * @property tamano Nombre o descripción del tamaño.
 * @property pvp Precio de venta al público del tamaño.
 */
data class TamaniosModel(
    val id: Int?,
    val tamano: String,
    val pvp: Double
)
