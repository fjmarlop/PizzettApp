package es.fjmarlop.pizzettApp.core.navigation

sealed class Rutas(val ruta: String) {
    object WelcomeScreen : Rutas("WelcomeScreen")
    object LoginScreen : Rutas("SignIn")
    object ProfileScreen : Rutas("ProfileScreen")
    object CreateAccountScreen : Rutas("CreateAccountScreen")
    object MainScreen : Rutas("MainScreen")
    object OfferScreen : Rutas("OfertasScreen")
    object RecoveryPasswordScreen : Rutas("RecuperarContrasenaScreen")
    object SignInEmail : Rutas("SignInEmail")
    object PrivacyPolices : Rutas("PrivacyPolices")
    object TermOfUses : Rutas("TermOfUses")
    object DetailsProfile: Rutas("DetailsProfileScreen")
    object Address: Rutas("AddressScreen")
    object Pizzas: Rutas("PizzasScreen")

}