package es.fjmarlop.pizzettApp.views.welcomeView.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import es.fjmarlop.pizzeta.R
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(welcomeViewModel: WelcomeViewModel, navHostController: NavHostController){

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Image(
            painter = painterResource(id = R.drawable.fondo_bienvenida),
            contentDescription = "BackGround App",
            Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )
        CircularProgressIndicator(modifier = Modifier.padding(32.dp))
        LaunchedEffect(key1 = true) {
            delay(3000)
            welcomeViewModel.goToLoginScreen(navHostController)
        }
    }

}