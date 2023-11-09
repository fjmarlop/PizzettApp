package es.fjmarlop.pizzettApp.vistas.cliente.compra.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import es.fjmarlop.pizzeta.R
import es.fjmarlop.pizzettApp.dataBase.Remote.models.LineaPedidoModel
import es.fjmarlop.pizzettApp.dataBase.local.models.AddressModel
import es.fjmarlop.pizzettApp.dataBase.local.models.UserModel
import es.fjmarlop.pizzettApp.vistas.cliente.main.ui.MainScafold
import es.fjmarlop.pizzettApp.vistas.cliente.main.ui.MainViewModel
import kotlin.math.roundToInt

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

    //CONSEGUIR DATOS USER DE ROOM
    val user by compraViewModel.user.observeAsState()

    //CONSEGUIR LIBRETRA DE DIRECCIONES
    val direcciones by compraViewModel.listAddress.observeAsState()

    //lISTA DE PEDIDOS
    val listaPedido by mainViewModel.listaLineasPedido.collectAsState()

    var showPedido by remember {
        mutableStateOf(true)
    }

    var showTramitarCompra by remember {
        mutableStateOf(false)
    }

    val domicilio by compraViewModel.domicilio.collectAsState()

    val local by compraViewModel.local.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(18.dp)
    ) {

        /**
         * Primera parte del pedido del cliente que corresponde con las líneas del pedido.
         * */

        if (showPedido) {
            ListaPedido(listaPedido = listaPedido, Modifier.weight(3f)) {
                mainViewModel.deleteLinea(it.idLineaPedidoModel)
                mainViewModel.onClickCarrito(2, navHostController)
            }

            if (listaPedido.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 18.dp)
                        .weight(2f), contentAlignment = Alignment.BottomCenter
                ) {
                    Column {
                        TotalesCarrito(listaPedido = listaPedido)
                        Spacer(modifier = Modifier.size(8.dp))
                        Button(
                            onClick = {
                                showPedido = false
                                showTramitarCompra = true
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Tramitar compra", fontSize = 24.sp,
                                fontFamily = FontFamily(
                                    Font(R.font.roboto_condensed)
                                ),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
        /**
         * Segunda parte del pedido del cliente que corresponden a los datos y la forma de entrega.
         * */

        if (showTramitarCompra) {

            IconButton(onClick = { mainViewModel.onClickCarrito(2, navHostController) }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "volver",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.size(12.dp))
            TipoPedido(
                local = local,
                domicilio = domicilio,
                modifier = Modifier.fillMaxWidth(),
                onClickDomicilio = {
                    compraViewModel.onChangeDomicilio()
                },
                onClickLocal = {
                    compraViewModel.onChangleLocal()

                })

            LaunchedEffect(true) {
                compraViewModel.getUser()
                user?.let { compraViewModel.getListAddress(it.email) }
            }
            Spacer(modifier = Modifier.size(12.dp))
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.TopStart) {
                Column {

                    user?.let {
                        DatosUsuario(user = it, onClickDetalles = {/*TODO navegar pantalla de detalles*/})
                    }
                    Spacer(modifier = Modifier.size(12.dp))

                    if (!local) {
                        RecogidaSelected()
                    }
                    else {
                        direcciones?.let { DireccionesUsuario(list = it, onClickAddAddress = {}) }
                    }
                }
            }
        }
    }
}


@Composable
fun TipoPedido(
    local: Boolean,
    domicilio: Boolean,
    modifier: Modifier,
    onClickLocal: () -> Unit,
    onClickDomicilio: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        OutlinedButton(
            onClick = {
                //RECOGIDA EN LOCAL
                onClickLocal()
            },
            modifier = Modifier.weight(1f),
            enabled = local
        ) {
            Icon(imageVector = Icons.Filled.ShoppingBag, contentDescription = null)
            Text(text = "Recogida", modifier = Modifier.padding(horizontal = 8.dp))

        }
        Spacer(modifier = Modifier.width(10.dp))
        OutlinedButton(
            onClick = {
                // PEDIDO A DOMICILIO
                onClickDomicilio()
            },
            enabled = domicilio,
            modifier = Modifier.weight(1f)
        ) {
            Icon(imageVector = Icons.Filled.DirectionsBike, contentDescription = null)
            Text(text = "A domicilio", modifier = Modifier.padding(horizontal = 8.dp))
        }
    }
}

@Composable
fun ListaPedido(
    listaPedido: List<LineaPedidoModel>,
    modifier: Modifier,
    onClickDelete: (LineaPedidoModel) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
        // .padding(horizontal = 16.dp)
    ) {
        if (listaPedido.isEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.size(50.dp))
                Text(
                    text = "Tu carrito está vacío.",
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.size(30.dp))
                Image(
                    painter = painterResource(id = R.drawable.keepcalm),
                    contentDescription = "Keep Calm",
                    modifier = Modifier
                        .size(300.dp)
                        .clip(RoundedCornerShape(30.dp))
                )
            }
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
                    Divider(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(vertical = 6.dp)
                    )
                    LazyColumn {
                        items(listaPedido) { item ->
                            ListaPedidoItem(
                                linea = item,
                                onClickDelete = { onClickDelete(item) })
                        }
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                }
            }

        }
    }
}

@Composable
fun TotalesCarrito(listaPedido: List<LineaPedidoModel>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Total pedido",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Box {
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row {
                    Text(
                        text = "Subtotal: ",
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                    Text(
                        text = getSubTotal(listaPedido).toString() + " €",
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
                Row {
                    Text(
                        text = "IVA 21%: ",
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                    Text(
                        text = getIva(listaPedido).toString() + " €",
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
                Row {
                    Text(
                        text = "Gastos de envío: ",
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                    Text(
                        text = getGastosEnvio(1.5).toString() + " €",
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
                Row {
                    Text(
                        text = "Total:   ",
                        modifier = Modifier.padding(vertical = 2.dp),
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = getTotal(listaPedido).toString() + " €",
                        modifier = Modifier.padding(vertical = 2.dp),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
        Divider(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 12.dp)
        )
    }
}

private fun getGastosEnvio(gastos: Double) = gastos // DE MOMENTO LOS GASTOS DE ENVÍO SON FIJOS

private fun getTotal(listaPedido: List<LineaPedidoModel>): Double {
    var total = 0.0
    listaPedido.forEach { ped -> total += (ped.cantidad * ped.producto.pvp) }
    return ((total + getGastosEnvio(1.5)) * 100.0).roundToInt() / 100.0
}

private fun getIva(listaPedido: List<LineaPedidoModel>): Double {
    val iva = getTotal(listaPedido) * 0.21
    return (iva * 100.0).roundToInt() / 100.0
}

private fun getSubTotal(listaPedido: List<LineaPedidoModel>): Double {
    val sub = getTotal(listaPedido) - getIva(listaPedido)
    return (sub * 100.0).roundToInt() / 100.0
}

@Composable
fun ListaPedidoItem(linea: LineaPedidoModel, onClickDelete: (LineaPedidoModel) -> Unit) {
    var tam = ""

    if (linea.producto.categoria == "Pizzas") {
        tam = linea.producto.tamano
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = linea.cantidad.toString(),
            modifier = Modifier
                .padding(5.dp)
                .clip(shape = CircleShape)
                .background(MaterialTheme.colorScheme.tertiary)
                .height(22.dp)
                .width(22.dp),
            color = Color.White,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
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
        IconButton(onClick = { onClickDelete(linea) }) {
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
fun DatosUsuario(user: UserModel, onClickDetalles: () -> Unit) {
    Column {
        Text(
            text = "Tus datos",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp),
        )
        Spacer(modifier = Modifier.size(8.dp))
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = Icons.Default.Email,
                contentDescription = "cuenta de correo",
                modifier = Modifier.padding(end = 8.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
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
        }
        Spacer(modifier = Modifier.size(8.dp))
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = Icons.Default.PermIdentity,
                contentDescription = "nombre de usuario",
                modifier = Modifier.padding(end = 8.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
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
        }
        Spacer(modifier = Modifier.size(8.dp))
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = Icons.Default.Phone,
                contentDescription = "nombre de usuario",
                modifier = Modifier.padding(end = 8.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
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
        }
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Pulsa aquí para añadir o modificar tus datos",
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
fun DireccionesUsuario(list: List<AddressModel>, onClickAddAddress: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (list.isEmpty()) {
            Text(
                text = "Error 404 - No hay direcciones",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign =
                TextAlign.Center,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "No tienes guardada ninguna dirección",
                fontWeight = FontWeight.Bold, fontSize = 20.sp, textAlign =
                TextAlign.Center
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "Pulsa aquí para añadir una",
                modifier = Modifier.clickable { onClickAddAddress() },
                textDecoration = TextDecoration.Underline,
                fontSize = 12.sp
            )
        } else if (list.size == 1) {
            val name = list.map { it.name }
            Text(text = name.toString())
            Text(text = list.map { it.address }.toString())
        } else {
            Text(text = "selecciona tu direccion")
        }
    }
}

@Composable
fun RecogidaSelected() {

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.logo_la_pizzetta),
            contentDescription = "logo",
            modifier = Modifier.size(60.dp)
        )
        Text(text = "Restaurante La Pizzetta", fontWeight = FontWeight.Bold, fontSize = 25.sp)
        Text(text = "Plaza de Las Tendillas, 5. Córdoba, 14001", fontSize = 18.sp)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Telefono(telefono = "957434482")
            Text(text = " -- ")
            Telefono(telefono = "677031515")
        }
    }
}

@Composable
fun Telefono(telefono: String) {
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                llamada(telefono, context)
            } else {
                Toast.makeText(context, "Tienes que habilitar los permisos", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    Text(text = telefono, fontSize = 18.sp,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier.clickable {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CALL_PHONE
                ) -> {
                    llamada(telefono, context)
                }

                else -> launcher.launch(Manifest.permission.CALL_PHONE)
            }
        })
}


private fun llamada(num: String, context: Context) {
    context.startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$num")))
}


