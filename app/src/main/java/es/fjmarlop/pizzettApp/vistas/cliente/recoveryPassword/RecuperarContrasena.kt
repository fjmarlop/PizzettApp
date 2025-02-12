package es.fjmarlop.pizzettApp.vistas.cliente.recoveryPassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import es.fjmarlop.pizzeta.R
import es.fjmarlop.pizzettApp.vistas.login.ui.LoginViewModel
import es.fjmarlop.pizzettApp.vistas.login.ui.Usuario

/**
 * @author Fco Javier Marmolejo López
 *
 *  Esta vista usa el viewModel del login para
 *  reutilizar el componente Usuario
 *
 */


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RecuperarContrasenaScreen(
    loginViewModel: LoginViewModel,
    navHostController: NavHostController
) {

    val usuario: String by loginViewModel.userEmail.observeAsState("")
    val isRecoveryEnable: Boolean by loginViewModel.isRecoveryEnable.collectAsState()

    val focusRequesterUser = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(true) {
        loginViewModel.resetView()
    }

    Column(
        Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CloseButtonRecovery(loginViewModel = loginViewModel, navController = navHostController)
        Box(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxSize()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Image(
                    painter = painterResource(id = R.drawable.logo_la_pizzetta),
                    contentDescription = "logo pizzetta"
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = "Restaurar contraseña",
                    fontFamily = FontFamily(Font(R.font.roboto_condensed)),
                    fontSize = 30.sp
                )
                Spacer(modifier = Modifier.size(18.dp))
                Usuario(
                    usuario = usuario,
                    focusRequester = focusRequesterUser,
                    onClickImeActionDone = {
                        keyboardController?.hide()
                    },
                    onTextChanged = { loginViewModel.onRecoveryTextChanged(it) })
                Spacer(modifier = Modifier.size(18.dp))
                OutlinedButton(
                    onClick = { loginViewModel.recoveryPassword(usuario, navHostController) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    enabled = isRecoveryEnable
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.LockReset,
                            contentDescription = "Reset password"
                        )
                        Text(text = "Restaurar", modifier = Modifier.padding(horizontal = 8.dp))
                    }
                }
            }
        }

    }
}

@Composable
fun CloseButtonRecovery(loginViewModel: LoginViewModel, navController: NavHostController) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        IconButton(onClick = { loginViewModel.goToEmailLogin(navController) }) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
        }
    }
}