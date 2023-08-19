package es.fjmarlop.pizzettApp.core.navegacion

sealed class Rutas(val ruta: String) {

    object WelcomeScreen: Rutas("WelcomeScreen")

    object LoginScreen: Rutas("SignIn")

    object ProfileScreen: Rutas("Profile")

    object CrearCuentaScreen: Rutas("CrearCuentaScreen")

    object MainScreen: Rutas("MainScreen")

    object OfertasScreen: Rutas("OfertasScreen")
}