package es.fjmarlop.pizzettApp.vistas.cliente.historial.ui

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import es.fjmarlop.pizzeta.R
import es.fjmarlop.pizzettApp.dataBase.Remote.models.PedidoModel
import es.fjmarlop.pizzettApp.vistas.cliente.main.ui.MainScafold
import es.fjmarlop.pizzettApp.vistas.cliente.main.ui.MainViewModel

@Composable
fun HistoricoScreen(
    historicoViewModel: HistoricoViewModel,
    navHostController: NavHostController,
    mainViewModel: MainViewModel
) {
    LaunchedEffect(true){
        historicoViewModel.startUpdating()
    }

    MainScafold(
        content = { VistaHistorico(historicoViewModel) },
        navHostController = navHostController,
        mainViewModel = mainViewModel
    )
}

@Composable
fun VistaHistorico(historicoViewModel: HistoricoViewModel) {

    val pedidos by historicoViewModel.pedidos.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.margen))
    ) {
        Text(text = "Tus pedidos", style = MaterialTheme.typography.titleLarge)
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        LazyColumn {
            items(pedidos) { pedido -> PedidoItem(pedido = pedido) }
        }
    }
}


@Composable
fun PedidoItem(pedido: PedidoModel) {

    var showDetalles by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { showDetalles = !showDetalles },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            Image(
                painter = painterResource(id = R.drawable.logo_la_pizzetta),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = pedido.tipoEntrega, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = pedido.estado + " - " + pedido.fechaPedido)
                Text(
                    text = "Total: " + pedido.total.toString() + " €",
                    fontWeight = FontWeight.Bold
                )
            }
        }
        if (showDetalles) {
            PedidoDetalle(pedido = pedido, show = showDetalles) { showDetalles = !showDetalles }
        }
    }
}


@Composable
fun PedidoDetalle(pedido: PedidoModel, show: Boolean, onDismiss: () -> Unit) {

    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
            ) {
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    IconButton(onClick = { onDismiss() }, modifier = Modifier.padding(6.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "close")
                    }
                }
                Text(
                    text = pedido.fechaPedido,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 26.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(8.dp)
                        )
                )
                Spacer(modifier = Modifier.size(12.dp))
                Text(
                    text = pedido.estado,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 26.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(8.dp)
                        )
                )
                Spacer(modifier = Modifier.size(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(8.dp)
                        )
                        
                ) {
                    Column(Modifier.padding(8.dp)) {
                        Text(
                            text = "Datos del cliente.",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(text = pedido.nombreCliente)
                        Text(text = pedido.emailCliente)
                        Text(text = pedido.telefonoCliente.ifEmpty { "" })
                        Text(
                            text = "Datos de entrega.",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        if (pedido.tipoEntrega == "Domicilio") {
                            Text(text = pedido.direccionEnvio)
                        } else {
                            Text(text = "Recogido en local")
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "Datos del pedido.",
                            style = MaterialTheme.typography.titleLarge
                        )
                        pedido.lineasPedido.forEach { linea ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = linea.cantidad.toString(),
                                    modifier = Modifier.padding(horizontal = 12.dp)
                                )
                                Text(
                                    text = linea.producto.nombre_producto,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "Total: " + pedido.total.toString() + " €",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }
                Spacer(modifier = Modifier.size(12.dp))
            }
        }
    }
}