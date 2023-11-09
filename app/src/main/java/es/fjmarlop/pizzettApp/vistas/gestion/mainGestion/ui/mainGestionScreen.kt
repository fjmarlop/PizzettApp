package es.fjmarlop.pizzettApp.vistas.gestion.mainGestion.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import es.fjmarlop.pizzettApp.vistas.login.domain.googleLogin.GoogleAuthUiClient

@Composable
fun MainGestionScreen(mainGestionViewModel: MainGestionViewModel,googleAuthUiClient: GoogleAuthUiClient, navHostController: NavHostController) {
    Column {

    Text("mainGestionScreen")
    Button(onClick = { mainGestionViewModel.cerrarSesion(googleAuthUiClient, navHostController) }) {
        Text("cerrarSesion")
    }

    }
}