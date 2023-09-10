package es.fjmarlop.pizzettApp.core.navigation

/**
 * @author Fco Javier Marmolejo López
 *
 *  sealed class s un tipo de clase especial que se utiliza para representar una
 *  jerarquía de clases en la que se sabe de antemano todos los subtipos posibles.
 *
 *  Maneja la ruta de todas las vistas de la aplicación
 *
 * */


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

}