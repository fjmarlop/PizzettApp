package es.fjmarlop.pizzettApp.core.roomDB.models

import es.fjmarlop.pizzettApp.core.retrofit.models.TamaniosModel

data class ProductModel(
    val id_producto: Int,
    val nombre_producto: String,
    val imagen_producto: String,
    val descripcion: String,
    val categoria: String,
    val ingredients: List<String>,
    val tamanios: List<TamaniosModel>
)