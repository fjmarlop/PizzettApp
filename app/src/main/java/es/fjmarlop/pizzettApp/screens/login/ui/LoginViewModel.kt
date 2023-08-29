package es.fjmarlop.pizzettApp.screens.login.ui

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
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.screens.login.domain.googleLogin.GoogleAuthUiClient
import es.fjmarlop.pizzettApp.screens.login.domain.googleLogin.SignInResult
import es.fjmarlop.pizzettApp.screens.login.domain.googleLogin.SignInState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val utils: Utils
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
        viewModelScope.launch(Dispatchers.Main) {
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(_userEmail.value.toString(), _password.value.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        utils.mensajeToast("Has iniciado sesión. ¡Bienvenid@!")
                        navHostController.popBackStack()
                        utils.navigateToMain(navHostController)
                    } else {
                        utils.mensajeToast("Error inicio de sesión, el usuario o la contraseña no son válidos")
                    }
                }
        }
    }

    fun goToCrearCuentaScreen(navHostController: NavHostController) {
        viewModelScope.launch(Dispatchers.Main) {
            utils.navigateToCrearCuenta(navHostController)
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
            utils.navigateToMain(navController)
        }
    }

    fun handleSignInSuccess(navController: NavHostController) {
        sendMessage("Has iniciado sesión. ¡Bienvenid@!")
        utils.navigateToMain(navController)
        resetState()
    }


    private fun resetState() {
        _state.update { SignInState() }
    }

    fun onPressEmailButton(navController: NavHostController) {
        utils.navigateToSignInEmail(navController)
    }

    fun sendMessage(msg: String) {
        utils.mensajeToast(msg)
    }


    fun navigateToMain(navController: NavHostController) {
        utils.navigateToMain(navController)
    }

    fun goToForgotPassword(navController: NavHostController) {
        utils.navigateToRecuperarContrasena(navController)
    }

    fun recoveryPassword(usuario: String, navController: NavHostController) {
        Firebase.auth.sendPasswordResetEmail(usuario)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    utils.mensajeToast("Mensaje enviado, revise su bandeja de correo")
                    navController.popBackStack()
                    utils.navigateToLogin(navController)
                }
            }
            .addOnFailureListener { task -> task.localizedMessage?.let { utils.mensajeToast(it) } }
    }

    fun goToBack(navController: NavHostController) {
        navController.popBackStack()
        utils.navigateToLogin(navController)
    }

    fun goToPrivacyPolices(navController: NavHostController) {
        utils.navigateToPrivacyPolices(navController)
    }

    fun goToTermOfUses(navController: NavHostController) {
        utils.navigateToTermsOfUses(navController)
    }

}