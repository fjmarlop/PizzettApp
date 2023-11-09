package es.fjmarlop.pizzettApp.vistas.welcome.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.navigation.Navegadores
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val navegadores: Navegadores,
) : ViewModel() {

    fun goToLoginScreen(navHostController: NavHostController) {
        viewModelScope.launch(Dispatchers.Main) {
            navHostController.popBackStack()
            navegadores.navigateToLogin(navHostController)
        }
    }

}