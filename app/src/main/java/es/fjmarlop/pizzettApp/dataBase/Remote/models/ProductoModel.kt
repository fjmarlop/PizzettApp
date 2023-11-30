package es.fjmarlop.pizzettApp.dataBase.Remote.models


/**
 * Clase de modelo que representa un producto.
 *
 * @property id_producto Identificador único del producto.
 * @property nombre_producto Nombre del producto.
 * @property imagen_producto URL o ruta de la imagen del producto.
 * @property descripcion Descripción del producto.
 * @property categoria Conjunto de modelos [CategoriaModel] que representan las categorías a las que pertenece el producto.
 * @property ingredients Conjunto de modelos [IngredientsModel] que representan los ingredientes del producto.
 * @property tamanios Conjunto de modelos [TamaniosModel] que representan los tamaños disponibles del producto.
 */
data class ProductoModel(
    val id_producto: Int,
    val nombre_producto: String,
    val imagen_producto: String,
    val descripcion: String,
    val categoria: Set<CategoriaModel>,
    val ingredients: Set<IngredientsModel>,
    val tamanios: Set<TamaniosModel>
)
