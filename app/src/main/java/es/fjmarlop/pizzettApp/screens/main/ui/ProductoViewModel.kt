package es.fjmarlop.pizzettApp.screens.main.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.models.databaseModels.ProductoModel
import es.fjmarlop.pizzettApp.screens.main.domain.ProductoDomainService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductoViewModel @Inject constructor(
    private val productoDomainService: ProductoDomainService,
    private val utils: Utils
) :
    ViewModel() {

    private val _categoria = MutableStateFlow("")
    val categoria: StateFlow<String> = _categoria


      private var _productsList = MutableStateFlow<List<ProductoModel>>(emptyList())
      val productsList: StateFlow<List<ProductoModel>> = _productsList

    private fun getProductosPorCategoria(cat: String) {
        viewModelScope.launch() {
            val list = productoDomainService.getProductosPorCategoria(cat)
            if (list.isEmpty()){
                utils.mensajeToast("No podemos mostrar los datos, inténtalo mas tarde. Disculpa las molestias")
            } else {
            _productsList.value = list
            }
        }
    }

    fun onClickCategoria(categoria: String) {
        _categoria.value = categoria
        getProductosPorCategoria(categoria)
    }

}


