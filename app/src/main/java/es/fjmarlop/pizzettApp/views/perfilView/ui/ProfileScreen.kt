package es.fjmarlop.pizzettApp.views.perfilView.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import es.fjmarlop.pizzettApp.views.loginView.domain.googleLogin.GoogleAuthUiClient
import es.fjmarlop.pizzettApp.views.loginView.domain.googleLogin.UserData
import es.fjmarlop.pizzettApp.views.mainScreen.ui.MainScafold
import es.fjmarlop.pizzettApp.views.mainScreen.ui.MainViewModel


@Composable
fun Profile(
    profileViewModel: ProfileViewModel,
    navController: NavHostController,
    googleAuthUiClient: GoogleAuthUiClient,
    mainViewModel: MainViewModel
) {
    ProfileScreen(
        userData = googleAuthUiClient.getSignedInUser(),
        onSignOut = {
            profileViewModel.onSignOut(googleAuthUiClient, navController)
        },
        navController = navController, mainViewModel = mainViewModel
    )
}


@Composable
fun ProfileScreen(
    userData: UserData?,
    onSignOut: () -> Unit,
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    MainScafold(
        content = { cuerpoProvisional(userData = userData) { onSignOut() } },
        navHostController = navController,
        mainViewModel = mainViewModel
    )

}

@Composable
fun cuerpoProvisional(
    userData: UserData?,
    onSignOut: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (userData?.profilePictureUrl != null) {
            AsyncImage(
                model = userData.profilePictureUrl,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        if (userData?.username != null) {
            Text(
                text = userData.username,
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onSignOut) {
            Text(text = "Sign out")
        }
    }
}
