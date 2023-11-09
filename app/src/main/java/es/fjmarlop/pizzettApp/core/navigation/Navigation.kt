package es.fjmarlop.pizzettApp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import es.fjmarlop.pizzettApp.vistas.cliente.address.ui.AddressScreen
import es.fjmarlop.pizzettApp.vistas.cliente.address.ui.AddressViewModel
import es.fjmarlop.pizzettApp.vistas.cliente.compra.ui.CompraScreen
import es.fjmarlop.pizzettApp.vistas.cliente.compra.ui.CompraViewModel
import es.fjmarlop.pizzettApp.vistas.cliente.conditions.PrivacyPolices
import es.fjmarlop.pizzettApp.vistas.cliente.conditions.TermsOfUses
import es.fjmarlop.pizzettApp.vistas.cliente.createAccount.ui.CreateAccountScreen
import es.fjmarlop.pizzettApp.vistas.cliente.createAccount.ui.CreateAccountViewModel
import es.fjmarlop.pizzettApp.vistas.cliente.detailsAccount.ui.DetailProfileViewModel
import es.fjmarlop.pizzettApp.vistas.cliente.detailsAccount.ui.DetailsProfileScreen
import es.fjmarlop.pizzettApp.vistas.cliente.main.ui.MainScreen
import es.fjmarlop.pizzettApp.vistas.cliente.main.ui.MainViewModel
import es.fjmarlop.pizzettApp.vistas.cliente.offers.ui.OfertasScreen
import es.fjmarlop.pizzettApp.vistas.cliente.offers.ui.OfertasViewModel
import es.fjmarlop.pizzettApp.vistas.cliente.profile.ui.ProfileScreen
import es.fjmarlop.pizzettApp.vistas.cliente.profile.ui.ProfileViewModel
import es.fjmarlop.pizzettApp.vistas.cliente.recoveryPassword.RecuperarContrasenaScreen
import es.fjmarlop.pizzettApp.vistas.gestion.mainGestion.ui.MainGestionScreen
import es.fjmarlop.pizzettApp.vistas.gestion.mainGestion.ui.MainGestionViewModel
import es.fjmarlop.pizzettApp.vistas.login.domain.googleLogin.GoogleAuthUiClient
import es.fjmarlop.pizzettApp.vistas.login.ui.LoginViewModel
import es.fjmarlop.pizzettApp.vistas.login.ui.SignIn
import es.fjmarlop.pizzettApp.vistas.login.ui.SignInEmail
import es.fjmarlop.pizzettApp.vistas.welcome.ui.WelcomeScreen
import es.fjmarlop.pizzettApp.vistas.welcome.ui.WelcomeViewModel

/**
 * @author Fco Javier Marmolejo López
 *
 * Fichero encargado de controlar la navegación dentro de la aplicación.
 * Controla todas las vistas que tiene la app.
 *
 * */

@Composable
fun Navegador(
    loginViewModel: LoginViewModel,
    welcomeViewModel: WelcomeViewModel,
    createAccountViewModel: CreateAccountViewModel,
    googleAuthUiClient: GoogleAuthUiClient,
    mainViewModel: MainViewModel,
    profileViewModel: ProfileViewModel,
    ofertasViewModel: OfertasViewModel,
    detailProfileViewModel: DetailProfileViewModel,
    addressViewModel: AddressViewModel,
    compraViewModel: CompraViewModel,
    mainGestionViewModel: MainGestionViewModel
) {

    val navigationController = rememberNavController()

    NavHost(
        navController = navigationController,
        startDestination = Rutas.WelcomeScreen.ruta
    ) {
        composable(Rutas.WelcomeScreen.ruta) {
            WelcomeScreen(
                welcomeViewModel = welcomeViewModel,
                navHostController = navigationController
            )
        }
        composable(Rutas.LoginScreen.ruta) {
            SignIn(
                loginViewModel = loginViewModel,
                navController = navigationController,
                googleAuthUiClient = googleAuthUiClient
            )
        }
        composable(Rutas.ProfileScreen.ruta) {
            ProfileScreen(
                profileViewModel = profileViewModel,
                navController = navigationController,
                googleAuthUiClient = googleAuthUiClient,
                mainViewModel = mainViewModel
            )
        }
        composable(Rutas.CreateAccountScreen.ruta) {
            CreateAccountScreen(
                createAccountViewModel = createAccountViewModel,
                navController = navigationController
            )
        }
        composable(Rutas.MainScreen.ruta) {
            MainScreen(
                mainViewModel = mainViewModel,
                navHostController = navigationController
            )
        }
        composable(Rutas.OfferScreen.ruta) {
            OfertasScreen(
                mainViewModel = mainViewModel,
                navHostController = navigationController
            )
        }
        composable(Rutas.RecoveryPasswordScreen.ruta) {
            RecuperarContrasenaScreen(
                loginViewModel = loginViewModel,
                navHostController = navigationController
            )
        }
        composable(Rutas.SignInEmail.ruta) {
            SignInEmail(loginViewModel = loginViewModel, navController = navigationController)
        }
        composable(Rutas.PrivacyPolices.ruta){
            PrivacyPolices()
        }
        composable(Rutas.TermOfUses.ruta){
            TermsOfUses()
        }
        composable(Rutas.DetailsProfile.ruta){
            DetailsProfileScreen(detailProfileViewModel = detailProfileViewModel, navigationController)
        }
        composable(Rutas.Address.ruta){
            AddressScreen(addressViewModel = addressViewModel, navigationController)
        }
        composable(Rutas.Compra.ruta){
            CompraScreen(mainViewModel = mainViewModel, compraViewModel = compraViewModel, navHostController = navigationController)
        }
        composable(Rutas.MainGestion.ruta){
           MainGestionScreen(mainGestionViewModel, googleAuthUiClient)
        }

    }
}