package es.fjmarlop.pizzettApp.vistas.cliente.profile.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.navigation.Navegadores
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.dataBase.local.models.UserModel
import es.fjmarlop.pizzettApp.vistas.cliente.profile.domain.ProfileDomainService
import es.fjmarlop.pizzettApp.vistas.login.domain.googleLogin.GoogleAuthUiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para la gestión del perfil de usuario.
 *
 * Este ViewModel utiliza el servicio del dominio [ProfileDomainService] para gestionar la lógica
 * relacionada con el perfil de usuario en la interfaz de usuario.
 *
 * @property utils Instancia de [Utils] utilizada para funciones de utilidad.
 * @property navegadores Instancia de [Navegadores] utilizada para la navegación en la aplicación.
 * @property profileDomainService Instancia de [ProfileDomainService] utilizado para acceder a la lógica del dominio de perfiles.
 */
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val utils: Utils,
    private val navegadores: Navegadores,
    private val profileDomainService: ProfileDomainService
) : ViewModel() {

    /**
     * LiveData que contiene la información del usuario actual.
     */
    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel> = _user

    /**
     * Realiza el cierre de sesión, eliminando los datos del usuario y navegando a la pantalla de inicio de sesión.
     *
     * @param googleAuthUiClient Instancia de [GoogleAuthUiClient] utilizado para gestionar la autenticación con Google.
     * @param navController Instancia de [NavHostController] para gestionar la navegación.
     */
    fun onSignOut(googleAuthUiClient: GoogleAuthUiClient, navController: NavHostController) {
        viewModelScope.launch(Dispatchers.IO) {
            profileDomainService.cleanDataBase()
        }
        viewModelScope.launch {
            googleAuthUiClient.signOut()
            utils.mensajeToast("Has finalizado sesión")
            navController.popBackStack()
            navegadores.navigateToLogin(navController)
        }
    }

    /**
     * Elimina la cuenta del usuario actual y realiza el cierre de sesión.
     *
     * @param googleAuthUiClient Instancia de [GoogleAuthUiClient] utilizado para gestionar la autenticación con Google.
     * @param navController Instancia de [NavHostController] para gestionar la navegación.
     */
    fun onClickEliminarCuenta(
        googleAuthUiClient: GoogleAuthUiClient,
        navController: NavHostController
    ) {
        val user = Firebase.auth.currentUser!!

        user.delete().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                utils.mensajeToast("La cuenta se ha eliminado correctamente")
                onSignOut(googleAuthUiClient, navController)
            }
        }.addOnFailureListener { task ->
            Exception(task).localizedMessage?.let { utils.mensajeToast(it) }
        }
    }

    /**
     * Navega a la pantalla de detalles del perfil.
     *
     * @param navController Instancia de [NavHostController] para gestionar la navegación.
     */
    fun goToDetailsProfile(navController: NavHostController) {
        navegadores.navigateToDetailsProfile(navController)
    }

    /**
     * Navega a la pantalla de dirección.
     *
     * @param navController Instancia de [NavHostController] para gestionar la navegación.
     */
    fun goToAddress(navController: NavHostController) {
        navegadores.navigateToAddress(navController)
    }

    /**
     * Obtiene la información del usuario y actualiza el LiveData [_user].
     */
    fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = profileDomainService.getUser()
            _user.postValue(user)
        }
    }
}
