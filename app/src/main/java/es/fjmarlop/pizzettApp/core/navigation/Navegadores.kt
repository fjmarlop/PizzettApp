package es.fjmarlop.pizzettApp.core.navigation

import androidx.navigation.NavHostController
import javax.inject.Inject


/**
 * @author Francisco J. Marmolejo López.
 * Esta clase implementa los métodos para navegar entre las pantallas
 * */
class Navegadores @Inject constructor(){

    /**
     * Método para navegar a la pantalla principal del cliente
     * @param navController
     * */
    fun navigateToMain(navController: NavHostController){
        navController.navigate(Rutas.MainScreen.ruta)
    }
    /**
     * Método para navegar a la pantalla de ofertas
     * @param navController
     * */
    fun navigateToOfertas(navController: NavHostController){
        navController.navigate(Rutas.OfferScreen.ruta)
    }
    /**
     * Método para navegar a la pantalla de login
     * @param navController
     * */
    fun navigateToLogin(navController: NavHostController){
        navController.navigate(Rutas.LoginScreen.ruta)
    }
    /**
     * Método para navegar a la pantalla del perfil del cliente
     * @param navController
     * */
    fun navigateToProfile(navController: NavHostController){
        navController.navigate(Rutas.ProfileScreen.ruta)
    }

    /**
     * Método para navegar a la pantalla crear cuenta mediante email y contraseña
     * @param navController
     * */
    fun navigateToCrearCuenta(navController: NavHostController){
        navController.navigate(Rutas.CreateAccountScreen.ruta)
    }

    /**
     * Método para navegar a la pantalla de recuperar contrasena
     * @param navController
     * */
    fun navigateToRecuperarContrasena(navController: NavHostController){
        navController.navigate(Rutas.RecoveryPasswordScreen.ruta)
    }

    /**
     * Método para navegar a la pantalla de inicio de sesion mediante email y contraseña
     * @param navController
     * */
    fun navigateToSignInEmail(navController: NavHostController){
        navController.navigate(Rutas.SignInEmail.ruta)
    }

    /**
     * Método para navegar a la pantalla de Politicas de privacidad
     * */
    fun navigateToPrivacyPolices(navController: NavHostController){
        navController.navigate(Rutas.PrivacyPolices.ruta)
    }

    /**
     * Método para navegar a la pantalla de terminos de uso
     * @param navController
     * */
    fun navigateToTermsOfUses(navController: NavHostController){
        navController.navigate(Rutas.TermOfUses.ruta)
    }

    /**
     * Método para navegar a la pantalla de detelles del cliente
     * @param navController
     * */
    fun navigateToDetailsProfile(navController: NavHostController){
        navController.navigate(Rutas.DetailsProfile.ruta)
    }

    /**
     * Método para navegar a la pantalla de direcciones
     * @param navController
     * */
    fun navigateToAddress(navController: NavHostController){
        navController.navigate(Rutas.Address.ruta)
    }

    /**
     * Método para navegar a la pantalla de compra (carrito)
     * @param navController
     * */
    fun navigateToCompra(navController: NavHostController){
        navController.navigate(Rutas.Compra.ruta)
    }

    /**
     * Método para navegar a la pantalla de principal de gestión
     * @param navController
     * */
    fun navigateToGestion(navController: NavHostController){
        navController.navigate(Rutas.MainGestion.ruta)
    }

    /**
     * Método para navegar a la pantalla de histórico de pedidos
     * @param navController
     * */
    fun navigateToHistorico(navController: NavHostController){
        navController.navigate(Rutas.Historico.ruta)
    }
}