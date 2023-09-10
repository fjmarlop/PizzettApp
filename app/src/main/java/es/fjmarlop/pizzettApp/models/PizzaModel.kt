package es.fjmarlop.pizzettApp.models

data class PizzaModel(
    val pizzaName: String,
    val pizzaImg: String,
    val ingredients: Set<IngredientsModel>,
    val tamanios: Set<TamaniosModel>
    )
