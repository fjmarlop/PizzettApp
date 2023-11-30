package es.fjmarlop.pizzettApp.dataBase.Remote.models

/**
 * Clase de modelo que representa una categoría.
 *
 * @property id_categoria Identificador único de la categoría.
 * @property nombre_categoria Nombre de la categoría.
 */
data class CategoriaModel(
    val id_categoria: Int,
    val nombre_categoria: String
)
