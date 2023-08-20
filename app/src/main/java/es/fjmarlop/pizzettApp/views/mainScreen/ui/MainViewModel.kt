package es.fjmarlop.pizzettApp.views.mainScreen.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.views.mainScreen.domain.GetEmailUseCase
import es.fjmarlop.pizzettApp.views.mainScreen.ui.MainUiState.Success
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val utils: Utils,
    getEmailUseCase: GetEmailUseCase
) : ViewModel() {

    private val _index = MutableLiveData<Int>()
    val index: LiveData<Int> = _index

    val uiState: StateFlow<MainUiState> = getEmailUseCase().map(::Success)
        .catch{MainUiState.Error(it)}
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MainUiState.Loading)

     fun onClickInicio(int: Int, navHostController: NavHostController) {
        _index.value = int
        utils.navigateToMain(navHostController)
    }


    fun onClickOfertas(int: Int, navHostController: NavHostController) {
        _index.value = int
        utils.navigateToOfertas(navHostController)
    }

    fun onClickCarrito(int: Int, navHostController: NavHostController) {
        _index.value = int
        // utils.navigateTo(navHostController, Rutas.MainScreen)
    }

    fun onClickPedidos(int: Int, navHostController: NavHostController) {
        _index.value = int
        //utils.navigateTo(navHostController, Rutas.MainScreen)
    }

    fun onClickCuenta(int: Int, navHostController: NavHostController) {
        _index.value = int
        utils.navigateToProfile(navHostController)
    }


}




