package es.fjmarlop.pizzettApp.screens.login.domain.googleLogin

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)