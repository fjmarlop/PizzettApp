package es.fjmarlop.pizzettApp.vistas.gestion.mainGestion.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import es.fjmarlop.pizzettApp.vistas.login.domain.googleLogin.GoogleAuthUiClient

@Composable
fun MainGestionScreen(mainGestionViewModel: MainGestionViewModel,googleAuthUiClient: GoogleAuthUiClient) {
    Column {

    Text("mainGestionScreen")
    Button(onClick = { mainGestionViewModel.cerrarSesion(googleAuthUiClient) }) {
        Text("cerrarSesion")
    }

    }
}