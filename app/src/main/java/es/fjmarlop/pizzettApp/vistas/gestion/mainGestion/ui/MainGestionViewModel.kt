package es.fjmarlop.pizzettApp.vistas.gestion.mainGestion.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.navigation.Navegadores
import es.fjmarlop.pizzettApp.vistas.login.domain.googleLogin.GoogleAuthUiClient
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainGestionViewModel @Inject constructor(private val navegadores: Navegadores): ViewModel() {


    fun cerrarSesion(googleAuthUiClient: GoogleAuthUiClient, navHostController: NavHostController) {
        viewModelScope.launch {
            googleAuthUiClient.signOut()
            navHostController.popBackStack()
            navegadores.navigateToLogin(navHostController)
        }
    }

}