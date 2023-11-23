package es.fjmarlop.pizzettApp.vistas.login.ui

import android.util.Patterns
import androidx.activity.result.ActivityResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.navigation.Navegadores
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.vistas.login.domain.ComprobarEmpleadoUseCase
import es.fjmarlop.pizzettApp.vistas.login.domain.googleLogin.GoogleAuthUiClient
import es.fjmarlop.pizzettApp.vistas.login.domain.googleLogin.SignInResult
import es.fjmarlop.pizzettApp.vistas.login.domain.googleLogin.SignInState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val utils: Utils,
    private val navegadores: Navegadores,
    private val comprobarEmpleadoUseCase: ComprobarEmpleadoUseCase
) :
    ViewModel() {

    private val userFirebase = Firebase.auth.currentUser

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> = _userEmail

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _isLoginEnable = MutableLiveData<Boolean>()
    val isLoginEnable: LiveData<Boolean> = _isLoginEnable

    private val _isRecoveryEnable = MutableStateFlow(false)
    val isRecoveryEnable = _isRecoveryEnable.asStateFlow()

    fun onLoginTextChanged(usuario: String, password: String) {
        _userEmail.value = usuario
        _password.value = password
        _isLoginEnable.value = enableLogin(usuario, password)

    }

    fun onRecoveryTextChanged(usuario: String) {
        _userEmail.value = usuario
        _isRecoveryEnable.value = enableRecovery(usuario)
    }

    private fun enableRecovery(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun enableLogin(email: String, password: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length >= 6
    }

    fun loginSessionEmail(navHostController: NavHostController) {
        viewModelScope.launch(Dispatchers.IO) {
            val isEmpleado = comprobarEmpleadoUseCase(_userEmail.value.toString())

            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(_userEmail.value.toString(), _password.value.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        if (isEmpleado == 1) {
                            viewModelScope.launch(Dispatchers.Main) {
                                navHostController.popBackStack()
                                navegadores.navigateToGestion(navHostController)
                            }
                        }
                        if (isEmpleado == 0) {
                            viewModelScope.launch(Dispatchers.Main) {
                                navHostController.popBackStack()
                                navegadores.navigateToMain(navHostController)
                            }
                        }
                    } else {
                        utils.mensajeToast("Error inicio de sesión, el usuario o la contraseña no son válidos")
                    }
                }
        }
    }

    fun goToCrearCuentaScreen(navHostController: NavHostController) {
        viewModelScope.launch(Dispatchers.Main) {
            navegadores.navigateToCrearCuenta(navHostController)
        }
    }

    private fun onSignInResult(result: SignInResult) {
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

    fun checkSignedInUser(
        navController: NavHostController,
        googleAuthUiClient: GoogleAuthUiClient
    ) {
        if (googleAuthUiClient.getSignedInUser() != null) {
            viewModelScope.launch(Dispatchers.Main) {
                navegadores.navigateToLogin(navController)
            }
        }
    }

    fun handleSignInSuccess(navController: NavHostController) {
        sendMessage("Has iniciado sesión. ¡Bienvenid@!")
        navegadores.navigateToMain(navController)
        resetState()
    }

    private fun clearTexts() {
        _userEmail.value = ""
        _password.value = ""
    }

    private fun resetState() {
        _state.update { SignInState() }
    }

    fun onPressEmailButton(navController: NavHostController) {
        navegadores.navigateToSignInEmail(navController)
    }

    fun sendMessage(msg: String) {
        utils.mensajeToast(msg)
    }


    fun navigateToMain(navController: NavHostController) {
        navegadores.navigateToMain(navController)
    }

    fun goToForgotPassword(navController: NavHostController) {
        navegadores.navigateToRecuperarContrasena(navController)
    }

    fun recoveryPassword(usuario: String, navController: NavHostController) {
        Firebase.auth.sendPasswordResetEmail(usuario)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    utils.mensajeToast("Mensaje enviado, revise su bandeja de correo")
                    navController.popBackStack()
                    navegadores.navigateToLogin(navController)
                }
            }
            .addOnFailureListener { task -> task.localizedMessage?.let { utils.mensajeToast(it) } }
    }

    fun goToBack(navController: NavHostController) {
        navController.popBackStack()
        navegadores.navigateToLogin(navController)
    }

    fun goToEmailLogin(navController: NavHostController) {
        navController.popBackStack()
        navegadores.navigateToSignInEmail(navController)
    }

    fun goToPrivacyPolices(navController: NavHostController) {
        navegadores.navigateToPrivacyPolices(navController)
    }

    fun goToTermOfUses(navController: NavHostController) {
        navegadores.navigateToTermsOfUses(navController)
    }

    fun resetView() {
        _userEmail.value = ""
        _isRecoveryEnable.value = false
    }


}