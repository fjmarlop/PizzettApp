package es.fjmarlop.pizzettApp.dataBase.Remote.models

data class ProductoModel(

    val id_producto: Int,

    val nombre_producto: String,

    val imagen_producto: String,

    val descripcion: String,

    val categoria: Set<CategoriaModel>,

    val ingredients: Set<IngredientsModel>,

    val tamanios: Set<TamaniosModel>

)
