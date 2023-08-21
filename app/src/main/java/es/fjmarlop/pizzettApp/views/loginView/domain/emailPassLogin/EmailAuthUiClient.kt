package es.fjmarlop.pizzettApp.views.loginView.domain.emailPassLogin

import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.views.loginView.data.LoginRepository
import javax.inject.Inject


class EmailAuthUiClient @Inject constructor(
    private val utils: Utils,
    private val loginRepository: LoginRepository
) {
    
    fun sigInEmailPass(usuario: String, password: String, navHostController: NavHostController) {
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(usuario, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    utils.mensajeToast("Has iniciado sesión. ¡Bienvenid@!")
                    navHostController.popBackStack()
                    utils.navigateToMain(navHostController)
                } else {
                    utils.mensajeToast("Error inicio de sesión, el usuario o la contraseña no son válidos")
                }
            }
    }
}


