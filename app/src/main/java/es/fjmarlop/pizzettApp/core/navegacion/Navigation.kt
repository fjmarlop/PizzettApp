package es.fjmarlop.pizzettApp.core.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import es.fjmarlop.pizzettApp.views.crearCuentaView.ui.CrearCuentaScreen
import es.fjmarlop.pizzettApp.views.crearCuentaView.ui.CrearCuentaViewModel
import es.fjmarlop.pizzettApp.views.loginView.domain.googleLogin.GoogleAuthUiClient
import es.fjmarlop.pizzettApp.views.loginView.ui.LoginViewModel
import es.fjmarlop.pizzettApp.views.loginView.ui.SignIn
import es.fjmarlop.pizzettApp.views.mainScreen.ui.MainScreen
import es.fjmarlop.pizzettApp.views.mainScreen.ui.MainViewModel
import es.fjmarlop.pizzettApp.views.ofertasView.ui.OfertasScreen
import es.fjmarlop.pizzettApp.views.ofertasView.ui.OfertasViewModel
import es.fjmarlop.pizzettApp.views.perfilView.ui.Profile
import es.fjmarlop.pizzettApp.views.perfilView.ui.ProfileViewModel
import es.fjmarlop.pizzettApp.views.welcomeView.ui.WelcomeScreen
import es.fjmarlop.pizzettApp.views.welcomeView.ui.WelcomeViewModel

@Composable
fun Navegador(
    loginViewModel: LoginViewModel,
    welcomeViewModel: WelcomeViewModel,
    crearCuentaViewModel: CrearCuentaViewModel,
    googleAuthUiClient: GoogleAuthUiClient,
    mainViewModel: MainViewModel,
    profileViewModel: ProfileViewModel,
    ofertasViewModel: OfertasViewModel
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
            Profile(
                profileViewModel = profileViewModel,
                navController = navigationController,
                googleAuthUiClient = googleAuthUiClient,
                mainViewModel = mainViewModel
            )
        }
        composable(Rutas.CrearCuentaScreen.ruta){
            CrearCuentaScreen(crearCuentaViewModel = crearCuentaViewModel, navController = navigationController)
        }
        composable(Rutas.MainScreen.ruta){
            MainScreen(mainViewModel = mainViewModel, navHostController = navigationController, googleAuthUiClient)
        }
        composable(Rutas.OfertasScreen.ruta){
            OfertasScreen(mainViewModel = mainViewModel, ofertasViewModel = ofertasViewModel ,navHostController = navigationController)
        }

    }
}