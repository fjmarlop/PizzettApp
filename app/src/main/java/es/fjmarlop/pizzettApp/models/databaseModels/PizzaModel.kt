package es.fjmarlop.pizzettApp.models.databaseModels

data class PizzaModel(
    val pizzaName: String,
    val pizzaImg: String,
    val ingredients: Set<IngredientsModel>,
    val tamanios: Set<TamaniosModel>
    )
