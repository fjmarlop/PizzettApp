package es.fjmarlop.pizzettApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint
import es.fjmarlop.pizzettApp.core.navegacion.Navegador
import es.fjmarlop.pizzettApp.ui.theme.PizzetaTheme
import es.fjmarlop.pizzettApp.views.crearCuentaView.ui.CrearCuentaViewModel
import es.fjmarlop.pizzettApp.views.loginView.domain.googleLogin.GoogleAuthUiClient
import es.fjmarlop.pizzettApp.views.loginView.ui.LoginViewModel
import es.fjmarlop.pizzettApp.views.mainScreen.ui.MainViewModel
import es.fjmarlop.pizzettApp.views.ofertasView.ui.OfertasViewModel
import es.fjmarlop.pizzettApp.views.perfilView.ui.ProfileViewModel
import es.fjmarlop.pizzettApp.views.welcomeView.ui.WelcomeViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }


    private val welcomeViewModel: WelcomeViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    private val crearCuentaViewModel: CrearCuentaViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private val profileViewModel: ProfileViewModel  by viewModels()
    private val ofertasViewModel: OfertasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PizzetaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Navegador(
                        loginViewModel = loginViewModel,
                        welcomeViewModel = welcomeViewModel,
                        crearCuentaViewModel = crearCuentaViewModel,
                        mainViewModel = mainViewModel,
                        profileViewModel = profileViewModel,
                        ofertasViewModel = ofertasViewModel,
                        googleAuthUiClient = googleAuthUiClient
                    )

                }
            }
        }
    }

}

