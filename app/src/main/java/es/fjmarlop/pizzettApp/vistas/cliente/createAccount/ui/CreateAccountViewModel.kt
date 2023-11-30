package es.fjmarlop.pizzettApp.vistas.cliente.createAccount.ui

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.navigation.Navegadores
import es.fjmarlop.pizzettApp.core.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para la creación de cuentas de usuario.
 *
 * Esta clase maneja la lógica relacionada con la creación de cuentas, validación de
 * información del usuario y la navegación entre destinos en la aplicación.
 *
 * @property utils Instancia de la clase [Utils] utilizada para funciones de utilidad.
 * @property navegadores Instancia de la clase [Navegadores] utilizada para la navegación en la aplicación.
 */
@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val utils: Utils,
    private val navegadores: Navegadores,
) : ViewModel() {

    /**
     * Maneja los cambios en el campo de correo electrónico durante la creación de cuenta.
     *
     * @param email Nuevo valor del correo electrónico.
     */
    fun onCreateEmailChanged(email: String) {
        _usuario.value = email
        _isEmailValido.value = isEmailValido(email)
        enableCreateButton()
    }

    /**
     * Maneja los cambios en el campo de contraseña durante la creación de cuenta.
     *
     * @param password Nuevo valor de la contraseña.
     */
    fun onCreatePassworChanged(password: String) {
        _password.value = password
        _isPasswordValida.value = isPasswordValida(password)
        enableCreateButton()
    }

    /**
     * Maneja los cambios en el campo de repetición de contraseña durante la creación de cuenta.
     *
     * @param password Nuevo valor de la contraseña de repetición.
     */
    fun onCreateReplyPasswordChanged(password: String) {
        _replyPassword.value = password
        _isRelyPasswordValida.value = isPasswordsIguales()
        enableCreateButton()
    }

    /**
     * Verifica si la contraseña cumple con ciertos requisitos de seguridad.
     *
     * @param password Contraseña a verificar.
     * @return `true` si la contraseña es válida, `false` en caso contrario.
     */
    private fun isPasswordValida(password: String): Boolean {
        val passwordLength = password.length >= 6
        val tenerUnNumero = password.any { it.isDigit() }
        val tenerUnaMayuscula = password.any { it.isUpperCase() }
        val tenerUnaMinuscula = password.any { it.isLowerCase() }
        return passwordLength && tenerUnNumero && tenerUnaMayuscula && tenerUnaMinuscula
    }

    /**
     * Verifica si las contraseñas de creación y repetición son iguales.
     *
     * @return `true` si las contraseñas son iguales, `false` en caso contrario.
     */
    private fun isPasswordsIguales(): Boolean {
        val pass: String = _password.value.toString()
        val replyPass: String = _replyPassword.value.toString()
        return pass == replyPass
    }

    /**
     * Verifica si el formato del correo electrónico es válido.
     *
     * @param email Correo electrónico a verificar.
     * @return `true` si el correo electrónico es válido, `false` en caso contrario.
     */
    private fun isEmailValido(email: String): Boolean {
        val emailPattern = Patterns.EMAIL_ADDRESS.matcher(email)
        return emailPattern.matches()
    }

    /**
     * Habilita o deshabilita el botón de creación según la validez de la información ingresada.
     */
    private fun enableCreateButton() {
        val email: Boolean = _isEmailValido.value == true
        val pass: Boolean = _isPasswordValida.value == true
        val replyPass: Boolean = _isRelyPasswordValida.value == true
        _isCreateEnable.value = email && pass && replyPass
    }

    /**
     * Inicia el proceso de creación de cuenta con Firebase, utilizando los valores de usuario y contraseña.
     *
     * @param navHostController Instancia de [NavHostController] para gestionar la navegación.
     */
    fun createAccount(navHostController: NavHostController) {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(
                    _usuario.value.toString(),
                    _password.value.toString()
                )
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                       viewModelScope.launch (Dispatchers.Main){
                           navHostController.popBackStack()
                           navegadores.navigateToLogin(navHostController)
                       }
                    } else {
                        utils.mensajeToastCorto("No se ha podido crear la cuenta")
                    }
                }
        }
    }
    /**
     * Navega hacia atrás en el flujo de la aplicación.
     *
     * @param navController Instancia de [NavHostController] para gestionar la navegación.
     */
    fun goToBack(navController: NavHostController) {
        navController.popBackStack()
        navegadores.navigateToSignInEmail(navController)
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