package es.fjmarlop.pizzettApp.vistas.welcome.ui

sealed class SplashDestination{
    object Login : SplashDestination()
    object MainCliente : SplashDestination()
    object MainGestion : SplashDestination()

}
