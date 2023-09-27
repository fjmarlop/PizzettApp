package es.fjmarlop.pizzettApp.screens.compra.ui

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.utils.Utils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CompraViewModel @Inject constructor(private val utils: Utils): ViewModel(){

    fun onClickTipoPedido(selected: Boolean) {
        _tipoPedido.value = !selected
    }

    fun goToDetalles(navHostController: NavHostController) {
        utils.navigateToDetailsProfile(navHostController)
    }

    fun msg(msg: String) {
        utils.mensajeToast(msg)
    }


    private val _tipoPedido = MutableStateFlow(false)
    val tipoPedido: StateFlow<Boolean> = _tipoPedido
}