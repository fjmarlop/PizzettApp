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
import es.fjmarlop.pizzettApp.core.navigation.Navegador
import es.fjmarlop.pizzettApp.ui.theme.PizzetaTheme
import es.fjmarlop.pizzettApp.vistas.cliente.address.ui.AddressViewModel
import es.fjmarlop.pizzettApp.vistas.cliente.compra.ui.CompraViewModel
import es.fjmarlop.pizzettApp.vistas.cliente.createAccount.ui.CreateAccountViewModel
import es.fjmarlop.pizzettApp.vistas.cliente.detailsAccount.ui.DetailProfileViewModel
import es.fjmarlop.pizzettApp.vistas.cliente.main.ui.MainViewModel
import es.fjmarlop.pizzettApp.vistas.cliente.offers.ui.OfertasViewModel
import es.fjmarlop.pizzettApp.vistas.cliente.profile.ui.ProfileViewModel
import es.fjmarlop.pizzettApp.vistas.gestion.mainGestion.ui.MainGestionViewModel
import es.fjmarlop.pizzettApp.vistas.login.domain.googleLogin.GoogleAuthUiClient
import es.fjmarlop.pizzettApp.vistas.login.ui.LoginViewModel
import es.fjmarlop.pizzettApp.vistas.welcome.ui.WelcomeViewModel


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
    private val createAccountViewModel: CreateAccountViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private val ofertasViewModel: OfertasViewModel by viewModels()
    private val detailProfileViewModel: DetailProfileViewModel by viewModels()
    private val addressViewModel: AddressViewModel by viewModels()
    private val compraViewModel: CompraViewModel by viewModels()
    private val mainGestionViewModel: MainGestionViewModel by viewModels()

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
                        createAccountViewModel = createAccountViewModel,
                        mainViewModel = mainViewModel,
                        profileViewModel = profileViewModel,
                        ofertasViewModel = ofertasViewModel,
                        detailProfileViewModel = detailProfileViewModel,
                        googleAuthUiClient = googleAuthUiClient,
                        addressViewModel = addressViewModel,
                        compraViewModel = compraViewModel,
                        mainGestionViewModel = mainGestionViewModel
                    )

                }
            }
        }
    }

    /**
     * Sobrescribo el método para anular su función y
     * forzar a navegar por la barra de aplicación.
     *
     * Buscar solución mas eficiente.
     * */
   // override fun onBackPressed(){

   // }

}

