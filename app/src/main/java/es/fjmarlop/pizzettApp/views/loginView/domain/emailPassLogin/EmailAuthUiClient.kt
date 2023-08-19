package es.fjmarlop.pizzettApp.views.loginView.domain.emailPassLogin

import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import es.fjmarlop.pizzettApp.core.navegacion.Rutas
import es.fjmarlop.pizzettApp.core.utils.Utils
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class EmailAuthUiClient @Inject constructor(private val utils: Utils) {


    suspend operator fun invoke(
        usuario: String,
        password: String,
        navHostController: NavHostController
    ) {
        coroutineScope {
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(usuario, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        utils.mensajeToast("Has iniciado sesión. ¡Bienvenid@!")
                        utils.navigateTo(navHostController, Rutas.MainScreen)
                        navHostController.popBackStack()
                    } else {
                        utils.mensajeToast("Error inicio de sesión, el usuario o la contraseña no son válidos")
                    }
                }
        }
    }

}