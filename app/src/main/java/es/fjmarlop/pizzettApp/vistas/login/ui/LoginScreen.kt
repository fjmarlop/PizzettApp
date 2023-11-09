package es.fjmarlop.pizzettApp.vistas.login.ui


import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import es.fjmarlop.pizzettApp.vistas.login.domain.googleLogin.GoogleAuthUiClient
import es.fjmarlop.pizzettApp.vistas.login.domain.googleLogin.SignInState
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun SignIn(
    loginViewModel: LoginViewModel,
    navController: NavHostController,
    googleAuthUiClient: GoogleAuthUiClient
) {

    val state by loginViewModel.state.collectAsState()

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
        state = state,
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
) {

    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            loginViewModel.sendMessage(error)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.margen)),
        // contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LogoRestaurante()
            Spacer(modifier = Modifier.size(48.dp))
            BotonSesionEmail { loginViewModel.onPressEmailButton(navController) }
            Spacer(modifier = Modifier.size(8.dp))
            BotonSesionGoogle { onSignInClick() }
            Spacer(modifier = Modifier.size(8.dp))
            BotonSesionFacebook(
                onAuthComplete = {
                    loginViewModel.navigateToMain(navController)
                },
                onAuthError = { loginViewModel.sendMessage("No ha sido posible el inicio de sesión con Facebook") })

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                Column {
                    Row(Modifier.fillMaxWidth()) {
                        Text(
                            text = "Política de privacidad.",
                            modifier = Modifier
                                .weight(1f)
                                .clickable { loginViewModel.goToPrivacyPolices(navController) },
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "Condiciones de uso.",
                            modifier = Modifier
                                .weight(1f)
                                .clickable { loginViewModel.goToTermOfUses(navController) },
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Medium
                        )
                    }
                Text(text = "Cuando inicia sesión acepta las politicas de privacidad y las condiciones de uso", fontSize = 8.sp)
                }

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
        fontFamily = FontFamily(Font(R.font.roboto_condensed)),
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.size(18.dp))
    Text(
        text = "Para acceder a todas las características y beneficios, necesitas iniciar sesión en tu cuenta." +
                " No te preocupes, ¡es súper fácil! Simplemente ingresa tus credenciales de inicio" +
                " de sesión. Si no tienes cuenta te animamos a crearte una. ¡Gracias por elegir la PizzettApp!",
        fontSize = 14.sp, textAlign = TextAlign.Justify
    )

}

@Composable
fun BotonSesionEmail(onPressButton: () -> Unit) {
    OutlinedButton(
        onClick = { onPressButton() },
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = Icons.Filled.Mail,
                contentDescription = "logo Email",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
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









