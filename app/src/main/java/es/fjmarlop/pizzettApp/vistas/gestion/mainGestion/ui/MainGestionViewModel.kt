package es.fjmarlop.pizzettApp.vistas.gestion.mainGestion.ui

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.navigation.Navegadores
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.vistas.login.domain.googleLogin.GoogleAuthUiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainGestionViewModel @Inject constructor(
    private val navegadores: Navegadores,
    private val utils: Utils,

): ViewModel() {

    private val _nombreEmpleado = MutableStateFlow("")
    val nombreEmpleado: MutableStateFlow<String> = _nombreEmpleado

    private val _emailEmpleado = MutableStateFlow("")
    val emailEmpleado: MutableStateFlow<String> = _emailEmpleado

    private val _validarEmail = MutableStateFlow(false)
    val validarEmail: MutableStateFlow<Boolean> = _validarEmail

    fun onTextEmpleadoChange(nombreEmpleado: String, emailEmpleado: String) {
        _nombreEmpleado.value = nombreEmpleado
        _emailEmpleado.value = emailEmpleado
        _validarEmail.value = Patterns.EMAIL_ADDRESS.matcher(emailEmpleado).matches()
    }


    fun cerrarSesion(googleAuthUiClient: GoogleAuthUiClient, navHostController: NavHostController) {
        viewModelScope.launch {
            googleAuthUiClient.signOut()
            navHostController.popBackStack()
            navegadores.navigateToLogin(navHostController)
        }
    }

}