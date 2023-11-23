package es.fjmarlop.pizzettApp.core.navigation

import androidx.navigation.NavHostController
import javax.inject.Inject

class Navegadores @Inject constructor(){

    fun navigateToMain(navController: NavHostController){
        navController.navigate(Rutas.MainScreen.ruta)
    }
    fun navigateToOfertas(navController: NavHostController){
        navController.navigate(Rutas.OfferScreen.ruta)
    }

    fun navigateToLogin(navController: NavHostController){
        navController.navigate(Rutas.LoginScreen.ruta)
    }

    fun navigateToProfile(navController: NavHostController){
        navController.navigate(Rutas.ProfileScreen.ruta)
    }

    fun navigateToCrearCuenta(navController: NavHostController){
        navController.navigate(Rutas.CreateAccountScreen.ruta)
    }

    fun navigateToRecuperarContrasena(navController: NavHostController){
        navController.navigate(Rutas.RecoveryPasswordScreen.ruta)
    }
    fun navigateToSignInEmail(navController: NavHostController){
        navController.navigate(Rutas.SignInEmail.ruta)
    }
    fun navigateToPrivacyPolices(navController: NavHostController){
        navController.navigate(Rutas.PrivacyPolices.ruta)
    }
    fun navigateToTermsOfUses(navController: NavHostController){
        navController.navigate(Rutas.TermOfUses.ruta)
    }
    fun navigateToDetailsProfile(navController: NavHostController){
        navController.navigate(Rutas.DetailsProfile.ruta)
    }

    fun navigateToAddress(navController: NavHostController){
        navController.navigate(Rutas.Address.ruta)
    }

    fun navigateToCompra(navController: NavHostController){
        navController.navigate(Rutas.Compra.ruta)
    }
    fun navigateToGestion(navController: NavHostController){
        navController.navigate(Rutas.MainGestion.ruta)
    }

    fun navigateToHistorico(navController: NavHostController){
        navController.navigate(Rutas.Historico.ruta)
    }
}