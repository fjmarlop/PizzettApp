package es.fjmarlop.pizzettApp.screens.pizzas.domain

import es.fjmarlop.pizzettApp.models.PizzaModel
import es.fjmarlop.pizzettApp.screens.pizzas.data.PizzasRepository
import javax.inject.Inject

class PizzasDomainService @Inject constructor(private val pizzasRepository: PizzasRepository) {

    suspend fun getAllPizzas():List<PizzaModel>{
        return pizzasRepository.getAllPizzas()
    }


}