package es.fjmarlop.pizzettApp.views.mainScreen.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.navegacion.Rutas
import es.fjmarlop.pizzettApp.core.utils.Utils
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val utils: Utils) : ViewModel() {

    private val _index = MutableLiveData<Int>()
    val index: LiveData<Int> = _index


    fun onClickInicio(int: Int, navHostController: NavHostController) {
        _index.value = int
        utils.navigateTo(navHostController, Rutas.MainScreen)

    }

    fun onClickOfertas(int: Int, navHostController: NavHostController) {
        _index.value = int
        utils.navigateTo(navHostController, Rutas.OfertasScreen)
    }

    fun onClickCarrito(int: Int, navHostController: NavHostController) {
        _index.value = int
        utils.navigateTo(navHostController, Rutas.MainScreen)
    }

    fun onClickPedidos(int: Int, navHostController: NavHostController) {
        _index.value = int
        utils.navigateTo(navHostController, Rutas.MainScreen)
    }

    fun onClickCuenta(int: Int, navHostController: NavHostController) {
        _index.value = int
        utils.navigateTo(navHostController, Rutas.ProfileScreen)
    }
}


