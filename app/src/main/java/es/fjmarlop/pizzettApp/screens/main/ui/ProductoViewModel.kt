package es.fjmarlop.pizzettApp.screens.main.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.retrofit.models.ProductoModel
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.screens.main.domain.ProductoDomainService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductoViewModel @Inject constructor(
    private val productoDomainService: ProductoDomainService,
    private val utils: Utils
) :
    ViewModel() {

    private val _categoria = MutableStateFlow("Hoy te recomendamos...")
    val categoria: StateFlow<String> = _categoria


    private var _productsList = MutableStateFlow<List<ProductoModel>>(emptyList())
    val productsList: StateFlow<List<ProductoModel>> = _productsList

    private var _productsListForRandom = MutableStateFlow<List<ProductoModel>>(emptyList())
    val productsListForRandom: StateFlow<List<ProductoModel>> = _productsListForRandom

    private val _showRecomendados = MutableStateFlow(true)
    val showRecomendados: StateFlow<Boolean> = _showRecomendados


    fun getProductosParaRecomendados() {

        viewModelScope.launch {
            val list: List<ProductoModel>
            val listRandom = mutableSetOf<ProductoModel>()
            withContext(Dispatchers.IO) {
                list = productoDomainService.getProductosParaRecomendados()
            }
            if (list.isNotEmpty()) {
                for (i in 1..4) {
                    listRandom.add(list.random())
                }
                _productsListForRandom.value = listRandom.toList()
            } else {
                utils.mensajeToast("No podemos mostrar los datos, inténtalo mas tarde.")
            }
        }
    }


    private fun getProductosPorCategoria(cat: String) {
        viewModelScope.launch() {
            val list = productoDomainService.getProductosPorCategoria(cat)
            if (list.isEmpty()) {
                utils.mensajeToast("No podemos mostrar los datos, inténtalo mas tarde.")
            } else {
                _productsList.value = list
            }
        }
    }

    fun onClickCategoria(categoria: String) {
        _categoria.value = categoria
        getProductosPorCategoria(categoria)
        _showRecomendados.value = false
    }


}


