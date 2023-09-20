package es.fjmarlop.pizzettApp.core.utils

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import es.fjmarlop.pizzettApp.core.navigation.Rutas
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class Utils @Inject constructor(
    private val context: Context
){

    /**
     * @author Fco Javier Marmolejo López
     * @return Token en formato String
     *
     * Este método consigue el tokenId del usuario de Firebase
     * para autentificar el token con el backEnd.
     *
     * */
    suspend fun getToken(): String {
        return suspendCoroutine { continuation ->
            val user = FirebaseAuth.getInstance().currentUser
            user?.getIdToken(true)?.addOnSuccessListener { result ->
                val idToken = result.token
                continuation.resume(idToken.toString())
            }?.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
        }
    }


  fun mensajeToast(msg: String) {
        Toast.makeText(
            context,
            msg,
            Toast.LENGTH_LONG
        ).show()
    }

    fun isEmailValid(email: String): Boolean {
        val emailPattern = Patterns.EMAIL_ADDRESS.matcher(email)
        return emailPattern.matches()
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
    fun navigateToDetailsProfile(navController: NavHostController){
        navController.navigate(Rutas.DetailsProfile.ruta)
    }

    fun navigateToAddress(navController: NavHostController){
        navController.navigate(Rutas.Address.ruta)
    }

    fun navigateToCompra(navController: NavHostController){
        navController.navigate(Rutas.Compra.ruta)
    }
}