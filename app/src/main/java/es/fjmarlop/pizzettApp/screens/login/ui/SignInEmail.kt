package es.fjmarlop.pizzettApp.screens.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import es.fjmarlop.pizzeta.R

@Composable
fun SignInEmail(loginViewModel: LoginViewModel, navController: NavHostController) {

    val emailUser: String by loginViewModel.userEmail.observeAsState(initial = "")
    val password: String by loginViewModel.password.observeAsState(initial = "")
    val isLoginEnable: Boolean by loginViewModel.isLoginEnable.observeAsState(initial = false)


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CloseButton(loginViewModel = loginViewModel, navController = navController)
        Box(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxSize()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                SignInEmailHeader()
                SignInEmailBody(emailUser, password, isLoginEnable, loginViewModel, navController)
            }
        }
    }

}

@Composable
fun CloseButton(loginViewModel: LoginViewModel, navController: NavHostController) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        IconButton(onClick = { loginViewModel.goToBack(navController) }) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
        }
    }
}

@Composable
fun SignInEmailHeader() {
    Image(
        painter = painterResource(id = R.drawable.logo_la_pizzetta),
        contentDescription = "logo restaurante",
        modifier = Modifier.padding(24.dp)
    )
    Text(
        text = "Iniciar Sesión",
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
        fontFamily = FontFamily(Font(R.font.roboto_condensed)),
        textAlign = TextAlign.Center
    )
}

@Composable
fun SignInEmailBody(
    usuario: String,
    password: String,
    isLoginEnable: Boolean,
    loginViewModel: LoginViewModel,
    navController: NavHostController
) {

    // Crear dos FocusRequester para controlar el foco entre los TextField
    val focusRequesterUser = remember { FocusRequester() }
    val focusRequesterPass = remember { FocusRequester() }

    Spacer(modifier = Modifier.size(16.dp))
    Usuario(
        usuario = usuario,
        focusRequester = focusRequesterUser,
        onClickImeActionDone = {
            focusRequesterPass.requestFocus()
        },
        onTextChanged = { loginViewModel.onLoginTextChanged(it, password) })
    Spacer(modifier = Modifier.size(12.dp))
    Password(
        password = password,
        focusRequester = focusRequesterPass,
        onTextChanged = { loginViewModel.onLoginTextChanged(usuario, it) })
    Spacer(modifier = Modifier.size(12.dp))
    LoginButton(
        loginEnable = isLoginEnable,
        loginViewModel = loginViewModel,
        navController = navController
    )
    Spacer(modifier = Modifier.size(12.dp))
    RegistrarCuenta { loginViewModel.goToCrearCuentaScreen(navController) }
    Spacer(modifier = Modifier.size(8.dp))
    OlvidoContraseña { loginViewModel.goToForgotPassword(navController) }
    Spacer(modifier = Modifier.size(16.dp))
}


@Composable
fun Usuario(
    usuario: String,
    focusRequester: FocusRequester,
    onClickImeActionDone: () -> Unit,
    onTextChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = usuario,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        label = { Text(text = "Correo electrónico") },
        placeholder = { Text(text = "Introduce direccion de correo") },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
            cursorColor = MaterialTheme.colorScheme.tertiary,
            focusedLabelColor = MaterialTheme.colorScheme.tertiary,
        ),
        keyboardActions = KeyboardActions(onDone = { onClickImeActionDone() })
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Password(password: String, focusRequester: FocusRequester, onTextChanged: (String) -> Unit) {
    var passwordVisibility by remember { mutableStateOf(false) }

    // Obtener el controlador de teclado
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        label = { Text(text = "Contraseña") },
        placeholder = { Text("Introduce tu contraseña") },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
            cursorColor = MaterialTheme.colorScheme.tertiary,
            focusedLabelColor = MaterialTheme.colorScheme.tertiary,
        ),
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        trailingIcon = {
            val imagen = if (passwordVisibility) {
                Icons.Filled.VisibilityOff
            } else {
                Icons.Filled.Visibility
            }
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(
                    imageVector = imagen,
                    contentDescription = "show password",
                    //tint = MaterialTheme.colorScheme.tertiary
                )
            }
        },
        visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
        })
    )
}

@Composable
fun LoginButton(
    loginEnable: Boolean,
    loginViewModel: LoginViewModel,
    navController: NavHostController
) {
    Button(
        onClick = {
            loginViewModel.loginSessionEmail(navController)
        },
        enabled = loginEnable,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = "Iniciar sesión")
    }
}

@Composable
fun OlvidoContraseña(onClickOlvidoContrasena: () -> Unit) {
    Text(
        text = "¿Has olvidado la contraseña?",
        modifier = Modifier.clickable { onClickOlvidoContrasena() })
}

@Composable
fun RegistrarCuenta(onClick: () -> Unit) {
    Row {
        Text(text = "¿Nuevo usuario?")
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "Crear cuenta",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { onClick() })
    }
}