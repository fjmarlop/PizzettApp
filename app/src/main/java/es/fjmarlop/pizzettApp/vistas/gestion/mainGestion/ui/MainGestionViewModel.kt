package es.fjmarlop.pizzettApp.vistas.gestion.mainGestion.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.vistas.login.domain.googleLogin.GoogleAuthUiClient
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainGestionViewModel @Inject constructor(): ViewModel() {


    fun cerrarSesion(googleAuthUiClient: GoogleAuthUiClient){
        viewModelScope.launch {
            googleAuthUiClient.signOut()
        }
    }

}