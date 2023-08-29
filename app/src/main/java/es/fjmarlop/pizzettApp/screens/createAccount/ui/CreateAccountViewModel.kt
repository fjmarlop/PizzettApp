package es.fjmarlop.pizzettApp.screens.createAccount.ui

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.utils.Utils
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(private val utils: Utils): ViewModel(){
    fun onCreateEmailChanged(email: String) {
        _usuario.value = email
        _isEmailValido.value = isEmailValido(email)
        enableCreateButton()
    }

    fun onCreatePassworChanged(password: String) {
        _password.value = password
        _isPasswordValida.value = isPasswordValida(password)
        enableCreateButton()
    }

    fun onCreateReplyPasswordChanged(password: String) {
        _replyPassword.value = password
        _isRelyPasswordValida.value = isPasswordsIguales()
        enableCreateButton()
    }

    private fun isPasswordValida(password: String): Boolean {
        val passwordLength = password.length >= 6
        val tenerUnNumero = password.any { it.isDigit() }
        val tenerUnaMayuscula = password.any { it.isUpperCase() }
        val tenerUnaMinuscula = password.any { it.isLowerCase() }
        return passwordLength && tenerUnNumero && tenerUnaMayuscula && tenerUnaMinuscula
    }

    private fun isPasswordsIguales(): Boolean {
        val pass: String = _password.value.toString()
        val replyPass: String = _replyPassword.value.toString()
        return pass == replyPass
    }

    fun isEmailValido(email: String): Boolean {
        val emailPattern = Patterns.EMAIL_ADDRESS.matcher(email)
        return emailPattern.matches()
    }

    private fun enableCreateButton() {
        val email: Boolean = _isEmailValido.value == true
        val pass: Boolean = _isPasswordValida.value == true
        val replyPass: Boolean = _isRelyPasswordValida.value == true
        _isCreateEnable.value = email && pass && replyPass
    }

    fun createAccount(navHostController: NavHostController) {

        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(_usuario.value.toString(), _password.value.toString())
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    utils.navigateToLogin(navHostController)
                } else {
                    utils.mensajeToast("No se ha podido crear la cuenta")
                }
            }
    }

    fun goToBack(navController: NavHostController) {
        navController.popBackStack()
        utils.navigateToLogin(navController)
    }

    private val _usuario = MutableLiveData<String>()
    val usuario: LiveData<String> = _usuario

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _replyPassword = MutableLiveData<String>()
    val replyPassword: LiveData<String> = _replyPassword

    private val _isCreateEnable = MutableLiveData<Boolean>()
    val isCreateEnable: LiveData<Boolean> = _isCreateEnable

    private val _isEmailValido = MutableLiveData<Boolean>()
    val isEmailValido: LiveData<Boolean> = _isEmailValido

    private val _isPasswordValida = MutableLiveData<Boolean>()
    val isPasswordValida = _isPasswordValida

    private val _isRelyPasswordValida = MutableLiveData<Boolean>()
    val isReplyPasswordValida = _isRelyPasswordValida
}