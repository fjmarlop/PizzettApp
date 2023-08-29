package es.fjmarlop.pizzettApp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import es.fjmarlop.pizzettApp.screens.conditions.PrivacyPolices
import es.fjmarlop.pizzettApp.screens.conditions.TermsOfUses
import es.fjmarlop.pizzettApp.screens.createAccount.ui.CreateAccountScreen
import es.fjmarlop.pizzettApp.screens.createAccount.ui.CreateAccountViewModel
import es.fjmarlop.pizzettApp.screens.detailsAccount.ui.DetailProfileViewModel
import es.fjmarlop.pizzettApp.screens.detailsAccount.ui.DetailsProfileScreen
import es.fjmarlop.pizzettApp.screens.login.domain.googleLogin.GoogleAuthUiClient
import es.fjmarlop.pizzettApp.screens.login.ui.LoginViewModel
import es.fjmarlop.pizzettApp.screens.login.ui.SignIn
import es.fjmarlop.pizzettApp.screens.login.ui.SignInEmail
import es.fjmarlop.pizzettApp.screens.main.ui.MainScreen
import es.fjmarlop.pizzettApp.screens.main.ui.MainViewModel
import es.fjmarlop.pizzettApp.screens.offers.ui.OfertasScreen
import es.fjmarlop.pizzettApp.screens.offers.ui.OfertasViewModel
import es.fjmarlop.pizzettApp.screens.profile.ui.ProfileScreen
import es.fjmarlop.pizzettApp.screens.profile.ui.ProfileViewModel
import es.fjmarlop.pizzettApp.screens.recoveryPassword.RecuperarContrasenaScreen
import es.fjmarlop.pizzettApp.screens.welcome.ui.WelcomeScreen
import es.fjmarlop.pizzettApp.screens.welcome.ui.WelcomeViewModel

@Composable
fun Navegador(
    loginViewModel: LoginViewModel,
    welcomeViewModel: WelcomeViewModel,
    createAccountViewModel: CreateAccountViewModel,
    googleAuthUiClient: GoogleAuthUiClient,
    mainViewModel: MainViewModel,
    profileViewModel: ProfileViewModel,
    ofertasViewModel: OfertasViewModel,
    detailProfileViewModel: DetailProfileViewModel
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
    }
}