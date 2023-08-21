package es.fjmarlop.pizzettApp.views.loginView.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import es.fjmarlop.pizzeta.R

@Composable
fun RecuperarContrasenaScreen(loginViewModel: LoginViewModel, navHostController: NavHostController) {

    val usuario: String by loginViewModel.usuario.observeAsState(initial = "")
    val password: String by loginViewModel.password.observeAsState(initial = "")

    Column(
        Modifier
            .fillMaxSize()
            .padding(18.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = "Restaurar contraseña",
            fontFamily = FontFamily(Font(R.font.roboto_condensed)),
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.size(18.dp))
        Usuario(
            usuario = usuario,
            onTextChanged = { loginViewModel.onLoginTextChanged(it, password) })
        Spacer(modifier = Modifier.size(10.dp))
        Button(onClick = { loginViewModel.restaurarContrasena(usuario, navHostController) }) {
            Text(text = "Restaurar")
        }
    }
}