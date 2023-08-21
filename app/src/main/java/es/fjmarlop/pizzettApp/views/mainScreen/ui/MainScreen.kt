package es.fjmarlop.pizzettApp.views.mainScreen.ui

import android.util.Patterns
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Wysiwyg
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.fjmarlop.pizzeta.R
import es.fjmarlop.pizzettApp.views.loginView.domain.googleLogin.GoogleAuthUiClient

@Composable
fun MainScreen(mainViewModel: MainViewModel, navHostController: NavHostController, gooleAuthUiClient: GoogleAuthUiClient) {
    MainScafold(
        content = { VistaHome(mainViewModel, gooleAuthUiClient) },
        navHostController = navHostController,
        mainViewModel
    )
}

@Composable
fun VistaHome(mainViewModel: MainViewModel, gooleAuthUiClient: GoogleAuthUiClient) {


    Column(modifier = Modifier.padding(horizontal = 16.dp)) {

        val user = Firebase.auth.currentUser
        var name = if (user?.displayName == null) user?.email else user?.displayName

        name?.let { obtenerUsername(it) }?.let { TextWelcome(nombre = it) }

        Spacer(modifier = Modifier.size(12.dp))
        TitleCarrusel(string = "¿Qué te apetece comer hoy?")
        DividerMain()
        Carrusel()
        Spacer(modifier = Modifier.size(24.dp))
        TitleCarrusel(string = "Conoce nuestros BestSeller")
        DividerMain()
    }
}

@Composable
fun TextWelcome(nombre: String) {
    Text(
        text = "Hola, $nombre.",
        modifier = Modifier.padding(16.dp),
        fontSize = 30.sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily(
            Font(R.font.roboto_condensed)
        )
    )
}


@Composable
fun DividerMain() {
    Divider(Modifier.padding(vertical = 6.dp), color = Color(0xFFBF0030), thickness = 2.dp)
}

@Composable
fun TitleCarrusel(string: String) {
    Text(
        text = string,
        fontSize = 20.sp,
        fontFamily = FontFamily(Font(R.font.roboto_condensed)),
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Carrusel() {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState, enabled = true)
            .clip(RoundedCornerShape(20.dp))
    )
    {
        ItemCarrusel("Ensaladas", imagen = R.drawable.ensalada)
        ItemCarrusel("Pizzas", imagen = R.drawable.pizzas)
        ItemCarrusel("Pastas", imagen = R.drawable.pastas)
        ItemCarrusel("Gratinados", imagen = R.drawable.gratinados)
        ItemCarrusel("Postres", imagen = R.drawable.postres)
        ItemCarrusel("Bebidas", imagen = R.drawable.bebidas)
    }
}

@Composable
fun ItemCarrusel(plato: String, @DrawableRes imagen: Int) {
    Column {
        ImagenCarrusel(imagen = imagen, plato)
        Text(
            text = plato,
            modifier = Modifier.padding(horizontal = 12.dp),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ImagenCarrusel(@DrawableRes imagen: Int, description: String) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .width(100.dp)
            .height(100.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 15.dp
        )
    ) {
        Column {
            Image(
                painter = painterResource(id = imagen),
                contentDescription = description,
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun MainScafold(
    content: @Composable () -> Unit,
    navHostController: NavHostController,
    mainViewModel: MainViewModel
) {

    val index: Int by mainViewModel.index.observeAsState(initial = 0)

    Scaffold(
        //topBar = { ToolBar(onClickMore = {}) },
        bottomBar = {
            BottomBar(
                index = index,
                onClickInicio = {
                    mainViewModel.onClickInicio(0, navHostController)
                },
                onClickOfertas = {
                    mainViewModel.onClickOfertas(1, navHostController)
                },
                onClickCarrito = {
                    mainViewModel.onClickCarrito(2, navHostController)
                },
                onClickMisPedidos = {
                    mainViewModel.onClickPedidos(3, navHostController)
                },
                onClickCuenta = {
                    mainViewModel.onClickCuenta(4, navHostController)
                },
            )
        }

    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBar(onClickMore: () -> Unit) {
    TopAppBar(
        title = { },
        //title = { Text(text = stringResource(id = R.string.app_name)) },
        colors = TopAppBarDefaults.topAppBarColors(
            //  containerColor = Color(0xFF126e0c),
            // titleContentColor = Color.White,
            // actionIconContentColor = Color.White
        ),
        actions = {
            IconButton(onClick = { onClickMore() }) {
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "Mas")
            }
        }
    )
}

@Composable
fun BottomBar(
    index: Int,
    onClickInicio: () -> Unit,
    onClickOfertas: () -> Unit,
    onClickCarrito: () -> Unit,
    onClickMisPedidos: () -> Unit,
    onClickCuenta: () -> Unit
) {

    NavigationBar() {
        NavigationBarItem(selected = index == 0, onClick = { onClickInicio() }, icon = {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Inicio"
            )
        }, label = { Text(text = "Inicio") })
        NavigationBarItem(selected = index == 1, onClick = { onClickOfertas() }, icon = {
            Icon(
                imageVector = Icons.Default.Percent,
                contentDescription = "Ofertas"
            )
        }, label = { Text(text = "Ofertas") })
        NavigationBarItem(selected = index == 2, onClick = { onClickCarrito() }, icon = {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Carrito"
            )
        }, label = { Text(text = "Compra") })
        NavigationBarItem(selected = index == 3, onClick = { onClickMisPedidos() }, icon = {
            Icon(
                imageVector = Icons.Default.Wysiwyg,
                contentDescription = "Mis Pedidos"
            )
        }, label = { Text(text = "Pedidos") })
        NavigationBarItem(selected = index == 4, onClick = { onClickCuenta() }, icon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Cuenta"
            )
        }, label = { Text(text = "Cuenta") })
    }
}

private fun obtenerUsername(email: String): String {
    val emailPattern = Patterns.EMAIL_ADDRESS.matcher(email)
    return if (emailPattern.matches()){
        val partes = email.split("@")
        partes.firstOrNull() ?: ""
    } else{
        val partes = email.split(" ")
        partes.firstOrNull() ?: ""
    }
}