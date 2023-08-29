package es.fjmarlop.pizzettApp.screens.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.screens.login.domain.googleLogin.GoogleAuthUiClient
import es.fjmarlop.pizzettApp.screens.profile.domain.ProfileDomainService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val utils: Utils,
    private val profileDomainService: ProfileDomainService
) : ViewModel() {


    fun onSignOut(googleAuthUiClient: GoogleAuthUiClient, navController: NavHostController) {
        viewModelScope.launch(Dispatchers.IO) {
            profileDomainService.cleanDataBase()
        }
        viewModelScope.launch {
            googleAuthUiClient.signOut()
            utils.mensajeToast("Has finalizado sesión")
            navController.popBackStack()
            utils.navigateToLogin(navController)
        }
    }

    fun onClickEliminarCuenta(
        googleAuthUiClient: GoogleAuthUiClient,
        navController: NavHostController
    ) {
        val user = Firebase.auth.currentUser!!

        user.delete().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                utils.mensajeToast("La cuenta se ha eliminado correctamente")
                onSignOut(googleAuthUiClient, navController)
            }
        }.addOnFailureListener { task ->
            Exception(task).localizedMessage?.let { utils.mensajeToast(it) }
        }
    }

    fun goToDetailsProfile(navController: NavHostController) {
        utils.navigateToDetailsProfile(navController)
    }

}