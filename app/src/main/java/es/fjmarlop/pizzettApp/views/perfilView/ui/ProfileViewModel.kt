package es.fjmarlop.pizzettApp.views.perfilView.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.views.loginView.domain.googleLogin.GoogleAuthUiClient
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val utils: Utils) : ViewModel() {


    fun onSignOut(googleAuthUiClient: GoogleAuthUiClient, navController: NavHostController){
        viewModelScope.launch {
            googleAuthUiClient.signOut()
            utils.mensajeToast("Has finalizado sesión")
            navController.popBackStack()
            utils.navigateToLogin(navController)
        }
    }
}