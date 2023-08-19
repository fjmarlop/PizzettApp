package es.fjmarlop.pizzettApp.views.loginView.ui

import android.util.Patterns
import androidx.activity.result.ActivityResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.navegacion.Rutas
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.views.loginView.domain.emailPassLogin.EmailAuthUiClient
import es.fjmarlop.pizzettApp.views.loginView.domain.googleLogin.GoogleAuthUiClient
import es.fjmarlop.pizzettApp.views.loginView.domain.googleLogin.SignInResult
import es.fjmarlop.pizzettApp.views.loginView.domain.googleLogin.SignInState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailAuthUiClient: EmailAuthUiClient,
    private val utils: Utils
) :
    ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val _usuario = MutableLiveData<String>()
    val usuario: LiveData<String> = _usuario

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _isLoginEnable = MutableLiveData<Boolean>()
    val isLoginEnable: LiveData<Boolean> = _isLoginEnable

    private val _isShowForm = MutableLiveData<Boolean>()
    val isShowForm: LiveData<Boolean> = _isShowForm

    fun onLoginTextChanged(usuario: String, password: String) {
        _usuario.value = usuario
        _password.value = password
        _isLoginEnable.value = enableLogin(usuario, password)
    }

    private fun enableLogin(email: String, password: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length >= 6
    }

    fun loginSessionEmail(navHostController: NavHostController) {
        viewModelScope.launch(Dispatchers.Main) {
            emailAuthUiClient.invoke(
                _usuario.value.toString(),
                _password.value.toString(),
                navHostController
            )
        }
    }

    fun goToCrearCuentaScreen(navHostController: NavHostController) {
        viewModelScope.launch(Dispatchers.Main) {
            _isShowForm.value = false
            utils.navigateTo(navHostController,Rutas.CrearCuentaScreen)
        }
    }

    fun onSignInResult(result: SignInResult) {
        _state.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }
    }

    fun handleGoogleSignInResult(result: ActivityResult, googleAuthUiClient: GoogleAuthUiClient) {
        viewModelScope.launch {
            val signInResult = googleAuthUiClient.signInWithIntent(
                intent = result.data ?: return@launch
            )
            onSignInResult(signInResult)
        }
    }

    fun checkSignedInUser(navController: NavHostController, googleAuthUiClient: GoogleAuthUiClient) {
        if (googleAuthUiClient.getSignedInUser() != null) {
            utils.navigateTo(navController, Rutas.MainScreen)
        }
    }

    fun handleSignInSuccess(navController: NavHostController) {
        enviarMensaje("Has iniciado sesión. ¡Bienvenid@!")
        utils.navigateTo(navController, Rutas.MainScreen)
        resetState()
    }

    private fun resetState() {
        _state.update { SignInState() }
    }

    fun onPressEmailButton() {
        _isShowForm.value = true
    }

    fun closeForm() {
        _isShowForm.value = false
    }

    fun enviarMensaje(msg: String) {
        utils.mensajeToast(msg)
    }

     fun navegarHacia(navController: NavHostController, ruta: Rutas) {
        utils.navigateTo(navController, ruta)
    }

}