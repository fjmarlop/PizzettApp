package es.fjmarlop.pizzettApp.vistas.welcome.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.navigation.Navegadores
import es.fjmarlop.pizzettApp.vistas.welcome.domain.ComprobarEmpleadoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * ViewModel para la pantalla de bienvenida.
 *
 * Este ViewModel utiliza el servicio de navegación [Navegadores] y el caso de uso [ComprobarEmpleadoUseCase]
 * para gestionar la lógica de la pantalla de bienvenida.
 *
 * @property navegadores Instancia de [Navegadores] utilizada para la navegación en la aplicación.
 * @property comprobarEmpleado Instancia de [ComprobarEmpleadoUseCase] utilizada para comprobar la existencia de un empleado.
 */
@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val navegadores: Navegadores,
    private val comprobarEmpleado: ComprobarEmpleadoUseCase
) : ViewModel() {

    private val isLoggedIn = Firebase.auth.currentUser != null
    private val email = Firebase.auth.currentUser?.email.toString()

    /**
     * Comprueba que el usuario logueado exista en la tabla de Empleados
     * Al hacerlo con una corutina suspendida esperamos que se resuelva
     * la consulta antes de continuar.
     * @return Int si el empleado existe (1) o no (0)
     */
    suspend fun checkEmpleado(): Int = suspendCoroutine { continuation ->
        viewModelScope.launch(Dispatchers.IO) {
            val result =comprobarEmpleado(email)
            continuation.resume(result)
        }
    }

    /**
     *  Chequea que haya un usuario logueado.
     *  Dependiendo del tipo de usuario navegaremos a una vista diferente.
     *  Si falla nos mandará a la vista de login.
     **/
    fun checkSession(isEmpleado: Int): SplashDestination {
        return if (isLoggedIn && isEmpleado == 0) {
            SplashDestination.MainCliente
        } else if (isLoggedIn && isEmpleado == 1) {
            SplashDestination.MainGestion
        } else SplashDestination.Login
    }

    /**
     * Método para navegar a la vista de login
     * @param navHostController
     * */
    fun navigateToLogin(navHostController: NavHostController) {
        viewModelScope.launch {
            navHostController.popBackStack()
            navegadores.navigateToLogin(navHostController)
        }
    }

    /**
     *  Método para navegar a la vista de gestión
     *  @param navHostController
     * */
    fun navigateToMainGestion(navHostController: NavHostController) {
        viewModelScope.launch {
            navHostController.popBackStack()
            navegadores.navigateToGestion(navHostController)
        }
    }

    /**
     * Método para navegar a la vista de cliente
     * @param navHostController
     * */
    fun navigateToMainCliente(navHostController: NavHostController) {
        viewModelScope.launch {
            navHostController.popBackStack()
            navegadores.navigateToMain(navHostController)
        }
    }

}