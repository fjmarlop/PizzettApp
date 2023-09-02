package es.fjmarlop.pizzettApp.screens.pizzas.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.models.PizzaModel
import es.fjmarlop.pizzettApp.screens.pizzas.domain.PizzasDomainService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PizzasViewModel @Inject constructor(private val pizzasDomainService: PizzasDomainService):ViewModel(){

    private val _pizzasList = mutableStateListOf<PizzaModel>()
    val pizzasList: List<PizzaModel> = _pizzasList

    fun getAllPizzas(){
        viewModelScope.launch(Dispatchers.IO) {
          val list = pizzasDomainService.getAllPizzas()
           _pizzasList.clear()
            _pizzasList.addAll(list)
        }
    }
}