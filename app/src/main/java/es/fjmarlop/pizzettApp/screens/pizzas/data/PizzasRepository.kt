package es.fjmarlop.pizzettApp.screens.pizzas.data

import es.fjmarlop.pizzettApp.core.retrofit.dao.PizzaDao
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.models.PizzaModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class PizzasRepository @Inject constructor(private val pizzaDao: PizzaDao, private val utils: Utils) {

    suspend fun getAllPizzas(): List<PizzaModel> {
        val token = withContext(Dispatchers.IO) { utils.getToken() }
        return withContext(Dispatchers.IO) { pizzaDao.getAllPizzas("Bearer $token") }
    }


}

