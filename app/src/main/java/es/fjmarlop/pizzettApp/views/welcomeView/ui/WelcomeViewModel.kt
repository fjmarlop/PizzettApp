package es.fjmarlop.pizzettApp.views.welcomeView.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.navegacion.Rutas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(): ViewModel() {

    fun goToLoginScreen(navHostController: NavHostController) {
        viewModelScope.launch(Dispatchers.Main){
            navHostController.popBackStack()
            navHostController.navigate(Rutas.LoginScreen.ruta)
        }
    }

}