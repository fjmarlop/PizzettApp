package es.fjmarlop.pizzettApp.core.utils

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import es.fjmarlop.pizzettApp.core.navigation.Rutas
import javax.inject.Inject


class Utils @Inject constructor(
    private val context: Context
){

  fun mensajeToast(msg: String) {
        Toast.makeText(
            context,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }


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
}