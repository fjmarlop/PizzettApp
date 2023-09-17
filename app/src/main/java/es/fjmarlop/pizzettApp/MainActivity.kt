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
import es.fjmarlop.pizzettApp.screens.address.ui.AddressViewModel
import es.fjmarlop.pizzettApp.screens.compra.ui.CompraViewModel
import es.fjmarlop.pizzettApp.screens.createAccount.ui.CreateAccountViewModel
import es.fjmarlop.pizzettApp.screens.detailsAccount.ui.DetailProfileViewModel
import es.fjmarlop.pizzettApp.screens.login.domain.googleLogin.GoogleAuthUiClient
import es.fjmarlop.pizzettApp.screens.login.ui.LoginViewModel
import es.fjmarlop.pizzettApp.screens.main.ui.MainViewModel
import es.fjmarlop.pizzettApp.screens.main.ui.ProductoViewModel
import es.fjmarlop.pizzettApp.screens.offers.ui.OfertasViewModel
import es.fjmarlop.pizzettApp.screens.profile.ui.ProfileViewModel
import es.fjmarlop.pizzettApp.screens.welcome.ui.WelcomeViewModel
import es.fjmarlop.pizzettApp.ui.theme.PizzetaTheme


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
    private val profileViewModel: ProfileViewModel  by viewModels()
    private val ofertasViewModel: OfertasViewModel by viewModels()
    private val detailProfileViewModel: DetailProfileViewModel by viewModels()
    private val addressViewModel: AddressViewModel by viewModels()
    private val productoViewModel: ProductoViewModel by viewModels()
    private val compraViewModel: CompraViewModel by viewModels()

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
                        productoViewModel = productoViewModel,
                        compraViewModel = compraViewModel
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

