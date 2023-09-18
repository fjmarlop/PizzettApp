package es.fjmarlop.pizzettApp.screens.compra.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import es.fjmarlop.pizzettApp.core.retrofit.models.LineaPedidoModel
import es.fjmarlop.pizzettApp.core.roomDB.models.UserModel
import es.fjmarlop.pizzettApp.screens.main.ui.MainScafold
import es.fjmarlop.pizzettApp.screens.main.ui.MainViewModel

@Composable
fun CompraScreen(
    mainViewModel: MainViewModel,
    compraViewModel: CompraViewModel,
    navHostController: NavHostController
) {
    MainScafold(
        content = {
            VistaCompra(
                mainViewModel = mainViewModel,
                compraViewModel,
                navHostController
            )
        },
        navHostController = navHostController,
        mainViewModel = mainViewModel
    )
}

@Composable
fun VistaCompra(
    mainViewModel: MainViewModel,
    compraViewModel: CompraViewModel,
    navHostController: NavHostController
) {

    val user = UserModel("fjmarlop@borrarlo.com", "Paco Pruebas", "")

    Column(modifier = Modifier.fillMaxSize()) {
        TipoPedido(compraViewModel)
        Box(modifier = Modifier.weight(1.3f)) {
            DatosUsurio(user, onClickDetalles = { compraViewModel.goToDetalles(navHostController) })
        }
        Box(modifier = Modifier.weight(1f)) {

        }
        Box(modifier = Modifier.weight(1f)) {
            ListaPedido(mainViewModel = mainViewModel)
        }

    }
}


@Composable
fun TipoPedido(compraViewModel: CompraViewModel) {

    val selected by compraViewModel.tipoPedido.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        OutlinedButton(
            onClick = { compraViewModel.onClickTipoPedido(selected) },
            modifier = Modifier.weight(1f),
            enabled = selected
        ) {
            Icon(imageVector = Icons.Filled.DirectionsBike, contentDescription = null)
            Text(text = "A domicilio", modifier = Modifier.padding(horizontal = 8.dp))
        }
        Spacer(modifier = Modifier.width(10.dp))
        OutlinedButton(
            onClick = { compraViewModel.onClickTipoPedido(selected) },
            enabled = !selected,
            modifier = Modifier.weight(1f)
        ) {
            Icon(imageVector = Icons.Filled.ShoppingBag, contentDescription = null)
            Text(text = "Recogida", modifier = Modifier.padding(horizontal = 8.dp))
        }
    }
}

@Composable
fun ListaPedido(mainViewModel: MainViewModel) {

    val listaPedido by mainViewModel.listaLineasPedido.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        if (listaPedido.isEmpty()) {
            Text(text = "No has añadido nada a tu pedido")
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column {
                    Text(
                        text = "Tu pedido",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    LazyColumn {
                        items(listaPedido) { ListaPedidoItem(linea = it) }
                    }
                }
            }
        }
    }
}

@Composable
fun ListaPedidoItem(linea: LineaPedidoModel) {
    var tam = ""

    if (linea.producto.categoria == "Pizzas") {
        tam = linea.producto.tamano
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = linea.cantidad.toString())
        Text(
            text = linea.producto.nombre_producto + " " + tam,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 6.dp)
        )
        Text(
            text = ((linea.producto.pvp * linea.cantidad).toString() + " €"),
            modifier = Modifier.padding(end = 6.dp)
        )
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
    Divider(Modifier.padding(vertical = 6.dp))
}

@Composable
fun DatosUsurio(user: UserModel, onClickDetalles: () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "Tus datos",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp),
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = user.email,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(vertical = 8.dp, horizontal = 12.dp),
            color = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = user.name.ifBlank { "Nombre" },
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(vertical = 8.dp, horizontal = 12.dp),
            color = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = user.phone.ifBlank { "Teléfono" },
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(vertical = 8.dp, horizontal = 12.dp),
            color = MaterialTheme.colorScheme.outline
        )
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Pulsa aquí si quieres modificar tus datos",
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clickable { onClickDetalles() }
                    .padding(8.dp),
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun TipoPedidoRecogida() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Text(text = "Has elegido recoger tu pedido en nuestro local")
    }
}

@Composable
fun TipoPedidoDomicilio(){
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Text(text = "Has elegido pedido ")
    }
}