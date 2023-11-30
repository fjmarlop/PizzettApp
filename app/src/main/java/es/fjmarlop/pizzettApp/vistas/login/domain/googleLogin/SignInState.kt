package es.fjmarlop.pizzettApp.vistas.login.domain.googleLogin

/**
 * Clase que representa el estado de un intento de inicio de sesión.
 *
 * @property isSignInSuccessful Indica si el inicio de sesión fue exitoso.
 * @property signInError Mensaje de error en caso de un fallo en el inicio de sesión, o nulo si fue exitoso.
 */
data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)