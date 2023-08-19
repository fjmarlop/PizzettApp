package es.fjmarlop.pizzettApp.views.loginView.domain.googleLogin

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)