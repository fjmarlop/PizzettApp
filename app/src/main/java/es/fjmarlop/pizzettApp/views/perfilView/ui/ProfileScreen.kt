package es.fjmarlop.pizzettApp.views.perfilView.ui

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
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.fjmarlop.pizzeta.R
import es.fjmarlop.pizzettApp.views.loginView.domain.googleLogin.GoogleAuthUiClient
import es.fjmarlop.pizzettApp.views.mainScreen.ui.MainScafold
import es.fjmarlop.pizzettApp.views.mainScreen.ui.MainViewModel

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    navController: NavHostController,
    googleAuthUiClient: GoogleAuthUiClient,
    mainViewModel: MainViewModel
) {
    MainScafold(
        navHostController = navController,
        mainViewModel = mainViewModel,
        content = {
            Perfil(
                profileViewModel = profileViewModel,
                navController = navController,
                googleAuthUiClient = googleAuthUiClient
            )

        }
    )
}

@Composable
fun Perfil(
    profileViewModel: ProfileViewModel,
    navController: NavHostController,
    googleAuthUiClient: GoogleAuthUiClient,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        PerfilHeader()
        Divider(Modifier.padding(horizontal = 10.dp, vertical = 5.dp), color = Color(0xFFBF0030))
        Spacer(modifier = Modifier.size(10.dp))
        PerfilBody(
            onclickDetallesCuenta = { /*TODO*/ },
            onclickAyuda = { /*TODO*/ },
            onclickLibretaDirecciones = { /*TODO*/ })
        Divider(Modifier.padding(horizontal = 10.dp, vertical = 5.dp), color = Color(0xFFBF0030))
        PerfilButtons(
            onclickEliminarCuenta = { profileViewModel.onClickEliminarCuenta(googleAuthUiClient, navController) },
            onclickCerrarSesion = { profileViewModel.onSignOut(googleAuthUiClient, navController) })

    }
}

@Composable
fun PerfilHeader() {
    val user = Firebase.auth.currentUser
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        ImagenPerfil(user = user)
        Column(modifier = Modifier.padding(horizontal = 14.dp)) {
            user?.displayName?.let {
                Text(
                    text = it,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                )
            }
            Spacer(modifier = Modifier.size(5.dp))
            user?.email?.let { Text(text = it) }
        }
    }
}

@Composable
fun ImagenPerfil(user: FirebaseUser?) {

    if (user?.photoUrl != null) {
        AsyncImage(
            model = user?.photoUrl,
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.avatar),
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun PerfilBody(
    onclickDetallesCuenta: () -> Unit,
    onclickAyuda: () -> Unit,
    onclickLibretaDirecciones: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Column {
            BodyItem(
                onclick = { onclickDetallesCuenta() },
                icon = Icons.Filled.Person,
                text = "Detalles de la cuenta"
            )
            BodyItem(
                onclick = { onclickLibretaDirecciones() },
                icon = Icons.Filled.ViewList,
                text = "Libreta de direcciones"
            )
            BodyItem(onclick = { onclickAyuda() }, icon = Icons.Filled.Help, text = "Ayuda")
        }
    }
}

@Composable
fun BodyItem(onclick: () -> Unit, icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onclick() }) {
        Image(imageVector = icon, contentDescription = text, modifier = Modifier.size(30.dp))
        Text(text = text, fontSize = 20.sp, modifier = Modifier.padding(horizontal = 12.dp))
    }
    Spacer(modifier = Modifier.size(16.dp))
}

@Composable
fun PerfilButtons(onclickEliminarCuenta: () -> Unit, onclickCerrarSesion: () -> Unit) {
    Column(
        //horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {

        OutlinedButton(onClick = { onclickEliminarCuenta() }, modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    imageVector = Icons.Filled.DeleteOutline,
                    contentDescription = "Eliminar Cuenta",
                    colorFilter = ColorFilter.tint(Color(0xFFBF0030))
                )
                Text(
                    text = "Eliminar cuenta",
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontSize = 18.sp
                )
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedButton(onClick = { onclickCerrarSesion() }, modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    imageVector = Icons.Filled.Logout,
                    contentDescription = "Cerrar sesion",
                    colorFilter = ColorFilter.tint(Color(0xFFBF0030))
                )
                Text(
                    text = "Desconectar",
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontSize = 18.sp
                )
            }
        }
    }
}






