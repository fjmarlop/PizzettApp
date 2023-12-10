package es.fjmarlop.pizzettApp.dataBase.Remote.models

/**
 * Clase de modelo que representa un ingrediente.
 *
 * @property ingredientName Nombre del ingrediente.
 * @property id Identificador único del ingrediente.
 */
data class IngredientsModel(
    val ingredientName: String,
    val id: Int?,
)


