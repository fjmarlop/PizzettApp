package es.fjmarlop.pizzettApp.views.loginView.ui


import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.fjmarlop.pizzeta.R
import es.fjmarlop.pizzettApp.core.navegacion.Rutas
import es.fjmarlop.pizzettApp.views.loginView.domain.googleLogin.GoogleAuthUiClient
import es.fjmarlop.pizzettApp.views.loginView.domain.googleLogin.SignInState
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@Composable
fun SignIn(
    loginViewModel: LoginViewModel,
    navController: NavHostController,
    googleAuthUiClient: GoogleAuthUiClient
) {

    val state by loginViewModel.state.collectAsState()
    val usuario: String by loginViewModel.usuario.observeAsState(initial = "")
    val password: String by loginViewModel.password.observeAsState(initial = "")
    val isLoginEnable: Boolean by loginViewModel.isLoginEnable.observeAsState(initial = false)
    val isShowForm: Boolean by loginViewModel.isShowForm.observeAsState(initial = false)

    LaunchedEffect(key1 = Unit) {
        loginViewModel.checkSignedInUser(navController, googleAuthUiClient)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == ComponentActivity.RESULT_OK) {
                loginViewModel.handleGoogleSignInResult(result, googleAuthUiClient)
            }
        }
    )

    LaunchedEffect(key1 = state.isSignInSuccessful) {
        if (state.isSignInSuccessful) {
            loginViewModel.handleSignInSuccess(navController)
        }
    }

    LoginScreen(
        usuario = usuario,
        password = password,
        isLoginEnable = isLoginEnable,
        state = state,
        show = isShowForm,
        loginViewModel = loginViewModel,
        navController = navController,
        onSignInClick = {
            loginViewModel.viewModelScope.launch {
                val signInIntentSender = googleAuthUiClient.signIn()
                launcher.launch(
                    IntentSenderRequest.Builder(
                        signInIntentSender ?: return@launch
                    ).build()
                )
            }
        })
}


@Composable
fun LoginScreen(
    state: SignInState,
    onSignInClick: () -> Unit,
    loginViewModel: LoginViewModel,
    navController: NavHostController,
    show: Boolean,
    usuario: String,
    password: String,
    isLoginEnable: Boolean
) {

    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            loginViewModel.enviarMensaje(error)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LogoRestaurante()
            Spacer(modifier = Modifier.size(48.dp))
            BotonSesionEmail { loginViewModel.onPressEmailButton() }
            Spacer(modifier = Modifier.size(8.dp))
            BotonSesionGoogle { onSignInClick() }
            Spacer(modifier = Modifier.size(8.dp))
            BotonSesionFacebook(
                onAuthComplete = {
                    loginViewModel.navegarHacia(
                        navController,
                        Rutas.MainScreen
                    )
                },
                onAuthError = { loginViewModel.enviarMensaje("No ha sido posible el inicio de sesión con Facebook") })
            Formulario(
                show = show,
                usuario = usuario,
                password = password,
                isLoginEnable = isLoginEnable,
                loginViewModel = loginViewModel,
                navController = navController
            ) {
                loginViewModel.closeForm()
            }
        }
    }
}

@Composable
fun LogoRestaurante() {
    Image(
        painter = painterResource(id = R.drawable.logo_la_pizzetta),
        contentDescription = "logo restaurante",
        modifier = Modifier.padding(24.dp)
    )
    Text(
        text = "Bienvenidos al restaurante",
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
        fontFamily = FontFamily(Font(R.font.roboto_condensed))
    )
}

@Composable
fun BotonSesionEmail(onPressButton: () -> Unit) {
    OutlinedButton(
        onClick = { onPressButton() },
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(imageVector = Icons.Default.Mail, contentDescription = "logo Email")
            Text(
                text = "Iniciar sesión con email",
                modifier = Modifier.padding(horizontal = 8.dp),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun BotonSesionGoogle(onSignInClick: () -> Unit) {
    OutlinedButton(onClick = { onSignInClick() }, modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.google_logo),
                contentDescription = "logo Google",
                modifier = Modifier
                    .size(24.dp)
                    .clip(shape = CircleShape)
            )
            Text(
                text = "Iniciar sesión con Google",
                modifier = Modifier.padding(horizontal = 8.dp),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun BotonSesionFacebook(
    onAuthComplete: () -> Unit,
    onAuthError: (Exception) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val loginManager = LoginManager.getInstance()
    val callbackManager = remember { CallbackManager.Factory.create() }
    val launcher = rememberLauncherForActivityResult(
        loginManager.createLogInActivityResultContract(callbackManager, null)
    ) {
        // nothing to do. handled in FacebookCallback
    }
    DisposableEffect(Unit) {
        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onCancel() {
                // do nothing
            }
            override fun onError(error: FacebookException) {
                onAuthError(error)
            }
            override fun onSuccess(result: LoginResult) {
                scope.launch {
                    val token = result.accessToken.token
                    val credential = FacebookAuthProvider.getCredential(token)
                    val authResult = Firebase.auth.signInWithCredential(credential).await()
                    if (authResult.user != null) {
                        onAuthComplete() // navega a pantalla principal
                    } else {
                        onAuthError(IllegalStateException("No ha sido posible inciar sesión Facebook"))
                    }
                }
            }
        })
        onDispose {
            loginManager.unregisterCallback(callbackManager)
        }
    }
    OutlinedButton(
        onClick = { launcher.launch(listOf("email", "public_profile")) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.facebook_logo),
                contentDescription = "logo Google",
                modifier = Modifier
                    .size(24.dp)
                    .clip(shape = CircleShape)
            )
            Text(
                text = "Iniciar sesión con Facebook",
                modifier = Modifier.padding(horizontal = 8.dp),
                fontSize = 16.sp
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Formulario(
    usuario: String,
    password: String,
    isLoginEnable: Boolean,
    show: Boolean,
    loginViewModel: LoginViewModel,
    navController: NavHostController,
    onDissmis: () -> Unit
) {
    if (show) {
        AlertDialog(
            onDismissRequest = { onDissmis() },
            modifier = Modifier.clip(RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = "Inicio de sesión",
                    fontFamily = FontFamily(Font(R.font.roboto_condensed)),
                    fontSize = 22.sp
                )
                Spacer(modifier = Modifier.size(16.dp))
                Usuario(
                    usuario = usuario,
                    onTextChanged = { loginViewModel.onLoginTextChanged(it, password) })
                Spacer(modifier = Modifier.size(12.dp))
                Password(
                    password = password,
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
                OlvidoContraseña()
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
fun OlvidoContraseña() {
    Text(text = "¿Has olvidado la contraseña?", modifier = Modifier.clickable { /*TODO*/ })
}

@Composable
fun RegistrarCuenta(onClick: () -> Unit) {
    Row {
        Text(text = "¿Nuevo usuario?")
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "Crear cuenta",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onClick() })
    }
}

@Composable
fun Usuario(usuario: String, onTextChanged: (String) -> Unit) {
    TextField(
        value = usuario,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        placeholder = { Text(text = "Email") },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0x0847BB02),
            focusedContainerColor = Color(0x0847BB02),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun Password(password: String, onTextChanged: (String) -> Unit) {
    var passwordVisibility by remember { mutableStateOf(false) }
    TextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        placeholder = { Text("Contraseña") },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0x0847BB02),
            focusedContainerColor = Color(0x0847BB02),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val imagen = if (passwordVisibility) {
                Icons.Filled.VisibilityOff
            } else {
                Icons.Filled.Visibility
            }
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(imageVector = imagen, contentDescription = "show password")
            }
        },
        visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }
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


