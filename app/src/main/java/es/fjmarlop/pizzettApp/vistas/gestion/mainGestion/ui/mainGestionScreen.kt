package es.fjmarlop.pizzettApp.vistas.gestion.mainGestion.ui

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Discount
import androidx.compose.material.icons.filled.EmojiPeople
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Reorder
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import es.fjmarlop.pizzeta.R
import es.fjmarlop.pizzettApp.dataBase.Remote.models.EmpleadoModel
import es.fjmarlop.pizzettApp.dataBase.Remote.models.IngredientsModel
import es.fjmarlop.pizzettApp.dataBase.Remote.models.PedidoModel
import es.fjmarlop.pizzettApp.dataBase.Remote.models.ProductoModel
import es.fjmarlop.pizzettApp.dataBase.Remote.models.TamaniosModel
import es.fjmarlop.pizzettApp.vistas.login.domain.googleLogin.GoogleAuthUiClient

@Composable
fun MainGestionScreen(
    mainGestionViewModel: MainGestionViewModel,
    googleAuthUiClient: GoogleAuthUiClient,
    navHostController: NavHostController
) {

    var selectedItem by remember { mutableIntStateOf(0) }

    val productos by mainGestionViewModel.listaProductos.collectAsState()
    val pedidos by mainGestionViewModel.listaPedidos.collectAsState()
    val empleados by mainGestionViewModel.listaEmpleados.collectAsState()

    val nombreEmpleado by mainGestionViewModel.nombreEmpleado.collectAsState()
    val emailEmpleado by mainGestionViewModel.emailEmpleado.collectAsState()
    val validarEmail by mainGestionViewModel.validarEmail.collectAsState()

    var showAddEmpleado by remember { mutableStateOf(false) }
    var showAddProducto by remember { mutableStateOf(false) }

    val ingredientes by mainGestionViewModel.listaIngredientes.collectAsState()

    val nombreProducto by mainGestionViewModel.nombreProducto.collectAsState()
    val descripcionProducto by mainGestionViewModel.descripcionProducto.collectAsState()
    val urlImagenProducto by mainGestionViewModel.urlImagenProducto.collectAsState()
    val pvp by mainGestionViewModel.pvp.collectAsState()

    var ocultarFav = 1f

    when (selectedItem) {
        1 -> {
            ocultarFav = 0f
        }

        2 -> {
            ocultarFav = 0f
        }
    }

    if (productos.isEmpty()) {
        LaunchedEffect(true) {
            mainGestionViewModel.getAllProductos()
        }
    }

    if (empleados.isEmpty()) {
        LaunchedEffect(true) {
            mainGestionViewModel.getAllEmpleados()
        }
    }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {
                if (selectedItem == 0) {
                    showAddProducto = true
                }
                if (selectedItem == 3) {
                    showAddEmpleado = true
                }
            }, Modifier.alpha(ocultarFav)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Add, contentDescription = "add")
                    Text(text = "Añadir")
                }
            }
        },

        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Modo gestión la PizzettApp",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                IconButton(
                    onClick = {
                        mainGestionViewModel.cerrarSesion(
                            googleAuthUiClient,
                            navHostController
                        )
                    }, Modifier
                        .padding(horizontal = 8.dp)
                        .weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "Logout",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        bottomBar = {
            MyNavigationBar(selectedItem) {
                selectedItem = it
            }
        })
    { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = paddingValues.calculateBottomPadding(),
                    top = paddingValues.calculateBottomPadding()
                )
        ) {
            ProductosFrame(
                show = selectedItem,
                list = productos
            ) {
                //Cuando cambio de vista se detiene la actualización de pedidos
                mainGestionViewModel.stopUpdating()
                mainGestionViewModel.eliminarProducto(it, navHostController)
            }
            OfertasFrame(show = selectedItem)

            PedidosFrame(
                show = selectedItem,
                list = pedidos
            ) { mainGestionViewModel.actualizarEstado(it) }
            EmpleadosFrame(
                show = selectedItem,
                list = empleados
            ) {
                //Cuando cambio de vista se detiene la actualización de pedidos
                mainGestionViewModel.stopUpdating()
                mainGestionViewModel.eliminarEmpleado(it)
            }
            //FUNCIONES AÑADIR TODAS DESDE EL MISMO BOTÓN
            if (showAddEmpleado) {
                AddEmpleado(
                    onDismissRequest = { showAddEmpleado = false },
                    onNameChange = { mainGestionViewModel.onTextEmpleadoChange(it, emailEmpleado) },
                    onEmailChange = {
                        mainGestionViewModel.onTextEmpleadoChange(
                            nombreEmpleado,
                            it
                        )
                    },
                    nombre = nombreEmpleado,
                    email = emailEmpleado,
                    validarEmail = validarEmail,
                    onClickAgregar = {
                        mainGestionViewModel.agregarEmpleado(emailEmpleado, nombreEmpleado)
                        showAddEmpleado = false
                    }
                )
            }
            if (showAddProducto) {
                AddProducto(
                    onDismissRequest = { showAddProducto = false },
                    nombre = nombreProducto,
                    onNombreChange = { mainGestionViewModel.onNombreProductoChange(it) },
                    descripcion = descripcionProducto,
                    onDescripcionChange = { mainGestionViewModel.onDescripcionProductoChange(it) },
                    urlImagen = urlImagenProducto,
                    onUrlImagenChange = { mainGestionViewModel.onUrlImagenProductoChange(it) },
                    ingredientes = ingredientes,
                    onClickIngrediente = { mainGestionViewModel.saveIngredientes(it) },
                    pvp = pvp,
                    onPvpChange = { mainGestionViewModel.onPvpChange(it) },
                    onSaveTamano = { mainGestionViewModel.saveTamano(it) },
                    onSaveCategoria = { mainGestionViewModel.saveCategoria(it) },
                    onSaveProducto = {
                        mainGestionViewModel.saveProducto(
                            nombreProducto, descripcionProducto, urlImagenProducto
                        )
                        showAddProducto = false

                    }
                )
            }
        }
    }
}

@Composable
fun PedidosFrame(
    show: Int,
    list: List<PedidoModel>,
    onClickActualizarEstado: (PedidoModel) -> Unit
) {


    if (show == 2) {

        Box(
            modifier = Modifier
                .fillMaxSize(), contentAlignment = Alignment.TopCenter
        ) {
            Column(Modifier.padding(horizontal = dimensionResource(id = R.dimen.margen))) {
                Text(
                    text = "PEDIDOS",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(8.dp))
                ListadoPedidos(list = list) { onClickActualizarEstado(it) }
            }
        }
    }
}

@Composable
fun ListadoPedidos(list: List<PedidoModel>, onClickActualizarEstado: (PedidoModel) -> Unit) {
    LazyColumn {
        items(list.sortedByDescending { it.fechaPedido }) { pedido ->
            PedidoItem(pedido = pedido) {
                onClickActualizarEstado(
                    it
                )
            }
        }
    }

}

@Composable
fun PedidoItem(pedido: PedidoModel, onClickActualizarEstado: (PedidoModel) -> Unit) {

    var color = Color(0x20BE0A0F)
    var showDetalles by remember { mutableStateOf(false) }

    when (pedido.estado) {
        "Confirmado" -> {
            color = Color(0xBAF7E656)
        }

        "Entregado" -> {
            color = Color(0x508BC34A)
        }

        "Cancelado" -> {
            color = Color(0x30696969)
        }
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = color),
        modifier = Modifier.clickable { showDetalles = !showDetalles }) {
        // reutilizo el detalle de pedido de la vista cliente.
        if (showDetalles) es.fjmarlop.pizzettApp.vistas.cliente.historial.ui.PedidoDetalle(
            pedido = pedido,
            show = showDetalles
        ) {
            showDetalles = !showDetalles
        }

        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            if (pedido.estado == "Confirmado" || pedido.estado == "Sin confirmar") {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = { onClickActualizarEstado(pedido) }) {
                        Icon(
                            imageVector = Icons.Default.CheckCircleOutline,
                            contentDescription = "Confirmar"
                        )
                    }
                    if (pedido.estado == "Sin confirmar") Text(
                        text = "Confirmar",
                        fontSize = 8.sp
                    )
                    if (pedido.estado == "Confirmado") Text(
                        text = "Entregar",
                        fontSize = 8.sp
                    )
                }
            }
            Column {
                Text(
                    text = "Correo: " + pedido.emailCliente,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Text(
                    text = "Nombre: " + pedido.nombreCliente,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    maxLines = 1
                )
                Text(
                    text = "Tipo de entrega: " + pedido.tipoEntrega,
                    modifier = Modifier.padding(horizontal = 8.dp), fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Fecha: " + pedido.fechaPedido,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Text(
                    text = "Total: " + pedido.total + " €", modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(), textAlign = TextAlign.End
                )
            }

        }
    }

    Divider(Modifier.padding(8.dp))
}

@Composable
fun OfertasFrame(show: Int) {
    if (show == 1) {
        Box(
            modifier = Modifier
                .fillMaxSize(), contentAlignment = Alignment.TopCenter
        ) {
            Text(text = "OFERTAS", style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Composable
fun EmpleadosFrame(show: Int, list: List<EmpleadoModel>, onClickDelete: (Int) -> Unit) {
    if (show == 3) {
        Box(
            modifier = Modifier
                .fillMaxSize(), contentAlignment = Alignment.TopCenter
        ) {
            Column(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.margen))) {
                Text(text = "EMPLEADOS", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.size(12.dp))
                LazyColumn {
                    items(list) { item ->
                        EmpleadoItem(
                            empleado = item,
                            onClickDelete = { onClickDelete(it) })
                    }
                }
            }
        }
    }
}


@Composable
fun EmpleadoItem(empleado: EmpleadoModel, onClickDelete: (Int) -> Unit) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Email: " + empleado.empleadoEmail,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = "Nombre: " + empleado.empleadoName,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
        IconButton(onClick = { empleado.id?.let { onClickDelete(it) } }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "delete",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }

    Divider(Modifier.padding(8.dp))
}

@Composable
fun ProductosFrame(show: Int, list: List<ProductoModel>, onClickEliminar: (Int) -> Unit) {

    if (show == 0) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "PRODUCTOS", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.size(12.dp))
                GetListadoProductos(list = list) { onClickEliminar(it) }
            }

        }
    }
}

@Composable
fun GetListadoProductos(list: List<ProductoModel>, onClickEliminar: (Int) -> Unit) {
    LazyColumn {
        items(list) { item ->
            ProductoItem(item = item) {
                item.id_producto?.let { it1 ->
                    onClickEliminar(
                        it1
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoItem(item: ProductoModel, onClickEliminar: (Int) -> Unit) {

    var show by remember { mutableStateOf(false) }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { show = !show }, verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(item.imagen_producto),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(shape = RoundedCornerShape(10.dp)),
        )
        Column(Modifier.padding(horizontal = 8.dp)) {
            Row {
                Text(text = "Categoria: ", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = item.categoria.map { cat -> cat.nombre_categoria }.toString()
                        .replace("[", "").replace("]", "")
                )
            }
            Row {
                Text(text = "Nombre: ", style = MaterialTheme.typography.titleMedium)
                Text(text = item.nombre_producto)
            }
        }
    }
    if (show) {
        ModalBottomSheet(
            onDismissRequest = { show = !show },
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp),
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        ) {
            Column {
                Text(
                    text = "PRODUCTO",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    textAlign = TextAlign.Center
                )
                Image(
                    painter = rememberAsyncImagePainter(item.imagen_producto),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(150.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.size(12.dp))
            }
            Row(Modifier.padding(vertical = 8.dp, horizontal = 24.dp)) {
                Text(text = "Categoria: ", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = item.categoria.map { cat -> cat.nombre_categoria }.toString()
                        .replace("[", "").replace("]", "")
                )
            }
            Row(Modifier.padding(vertical = 8.dp, horizontal = 24.dp)) {
                Text(text = "Nombre: ", style = MaterialTheme.typography.titleMedium)
                Text(text = item.nombre_producto)
            }
            Text(
                text = "Descripción: ",
                modifier = Modifier.padding(horizontal = 24.dp),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = item.descripcion,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp)
            )
            Text(
                text = "Ingredientes: ",
                modifier = Modifier.padding(horizontal = 24.dp),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = item.ingredients.map { ing -> ing.ingredientName }.toString()
                    .replace("[", "").replace("]", ""),
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp)
            )
            Text(
                text = "Tamaños y precios: ",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp)
            )
            item.tamanios.sortedBy { it.tamano }.forEach { tam ->
                Text(
                    text = tam.tamano + " - " + tam.pvp + " €",
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp)
                )
            }
            Spacer(modifier = Modifier.size(32.dp))
            OutlinedButton(
                onClick = { item.id_producto?.let { onClickEliminar(it) } }, Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "delete",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Eliminar",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                }
            }
        }
    }
}

@Composable
fun MyNavigationBar(
    selectedItem: Int,
    onselectedItem: (Int) -> Unit
) {
    val icProductos = Pair("Productos", Icons.Default.Inventory)
    val icOfertas = Pair("Ofertas", Icons.Default.Discount)
    val icPedidos = Pair("Pedidos", Icons.Default.Reorder)
    val icEmpleados = Pair("Empleados", Icons.Default.EmojiPeople)

    val items = listOf(icProductos, icOfertas, icPedidos, icEmpleados)

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.second, "null") },
                label = { Text(text = item.first) },
                selected = selectedItem == index,
                onClick = { onselectedItem(index) }
            )
        }
    }
}

@Composable
fun AddProducto(
    onDismissRequest: () -> Unit,
    nombre: String,
    onNombreChange: (String) -> Unit,
    descripcion: String,
    onDescripcionChange: (String) -> Unit,
    urlImagen: String,
    onUrlImagenChange: (String) -> Unit,
    ingredientes: List<IngredientsModel>,
    onClickIngrediente: (List<String>) -> Unit,
    pvp: String,
    onSaveCategoria: (String) -> Unit,
    onPvpChange: (String) -> Unit,
    onSaveTamano: (TamaniosModel) -> Unit,
    onSaveProducto: () -> Unit
) {


    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "close",
                modifier = Modifier
                    .clickable { onDismissRequest() }
                    .align(Alignment.End)
                    .padding(8.dp)
            )
            Text(text = "Añadir Producto", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.size(8.dp))
            OutlinedTextField(
                value = nombre,
                onValueChange = { onNombreChange(it) },
                label = { Text(text = "Nombre del producto") },
                maxLines = 1,
                singleLine = true
            )
            Spacer(modifier = Modifier.size(8.dp))
            OutlinedTextField(
                value = descripcion,
                onValueChange = { onDescripcionChange(it) },
                label = { Text(text = "Descripción del producto") },
                maxLines = 1,
                singleLine = true
            )
            Spacer(modifier = Modifier.size(8.dp))
            OutlinedTextField(
                value = urlImagen,
                onValueChange = { onUrlImagenChange(it) },
                label = { Text(text = "Dirección de la imagen") },
                maxLines = 1,
                singleLine = true
            )
            Spacer(modifier = Modifier.size(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                Column {
                    Text(
                        text = "Seleccionar categoría",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        textAlign = TextAlign.Start
                    )
                    Categorias { onSaveCategoria(it) }
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                Column {
                    Text(
                        text = "Seleccionar ingredientes",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        textAlign = TextAlign.Start
                    )
                    Ingredientes(ingredientes) { onClickIngrediente(it) }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)

            ) {
                Column {
                    Text(
                        text = "Seleccionar Tamaños y PVP",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        textAlign = TextAlign.Start
                    )
                    Tamanos(
                        pvp = pvp,
                        onPvpChanged = { onPvpChange(it) },
                        onSaveTamano = { onSaveTamano(it) })
                }
            }
            OutlinedButton(onClick = { onSaveProducto() }, modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(imageVector = Icons.Default.Save, contentDescription = "save")
                    Text(
                        text = "Guardar",
                        modifier = Modifier.padding(horizontal = 8.dp),
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
fun Tamanos(pvp: String, onPvpChanged: (String) -> Unit, onSaveTamano: (TamaniosModel) -> Unit) {

    // Estado para realizar un seguimiento del elemento seleccionado
    var selectedIndex by remember { mutableIntStateOf(0) }

    // Estado para rastrear si el menú está abierto o cerrado
    var expanded by remember { mutableStateOf(false) }

    val tamanos = listOf("normal", "mediana", "familiar", "único")

    Column {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
        ) {
            tamanos.forEachIndexed { index, item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        selectedIndex = index
                        expanded = false
                    })
            }
        }
        Surface(
            modifier = Modifier
                .clickable(
                    onClick = { expanded = true }
                )
                .border(0.5.dp, MaterialTheme.colorScheme.primary)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = tamanos[selectedIndex],
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        Spacer(modifier = Modifier.size(4.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            OutlinedTextField(
                value = pvp,
                onValueChange = { onPvpChanged(it) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                label = { Text(text = "PVP") },
                suffix = { Text(text = " €") },
                trailingIcon = {
                    IconButton(onClick = {
                        val tam = TamaniosModel(null, tamanos[selectedIndex], pvp.toDouble())
                        onSaveTamano(tam)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
            )

        }
    }
}

@Composable
fun Categorias(onSaveCategoria: (String) -> Unit) {

    val categorias = listOf("Ensaladas", "Pizzas", "Pastas", "Gratinados", "Postres", "Bebidas")

    // Estado para realizar un seguimiento del elemento seleccionado
    var selectedIndex by remember { mutableIntStateOf(0) }

    // Estado para rastrear si el menú está abierto o cerrado
    var expanded by remember { mutableStateOf(false) }

    Column {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(MaterialTheme.colorScheme.surface)
        ) {
            categorias.forEachIndexed { index, item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        selectedIndex = index
                        expanded = false
                        onSaveCategoria(item)
                    }
                )
            }
        }
        Surface(
            modifier = Modifier
                .clickable(
                    onClick = { expanded = true },
                )
                .border(0.5.dp, MaterialTheme.colorScheme.primary)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = categorias[selectedIndex],
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

}

@Composable
fun Ingredientes(ingredientes: List<IngredientsModel>, onSaveIngrediente: (List<String>) -> Unit) {

    // Estado para realizar un seguimiento del elemento seleccionado
    var selectedIndex by remember { mutableIntStateOf(0) }

    // Estado para rastrear si el menú está abierto o cerrado
    var expanded by remember { mutableStateOf(false) }

    var selectedIngredients by remember { mutableStateOf(emptyList<String>()) }

    Column {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
        ) {
            ingredientes.forEachIndexed { index, item ->
                DropdownMenuItem(
                    text = { Text(text = item.ingredientName) }, onClick = {
                        selectedIndex = index
                        expanded = false
                        selectedIngredients = selectedIngredients + item.ingredientName
                        onSaveIngrediente(selectedIngredients)
                    }
                )
            }
        }
        Surface(
            modifier = Modifier
                .clickable(
                    onClick = { expanded = true },
                )
                .border(0.5.dp, MaterialTheme.colorScheme.primary)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = ingredientes[selectedIndex].ingredientName,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        LazyRow(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(selectedIngredients) { ingredient ->
                Text(text = "$ingredient,")
            }
        }
    }
}

@Composable
fun AddEmpleado(
    onDismissRequest: () -> Unit,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    nombre: String, email: String,
    validarEmail: Boolean,
    onClickAgregar: () -> Unit
) {


    Dialog(onDismissRequest = { onDismissRequest() }) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "close",
                modifier = Modifier
                    .clickable { onDismissRequest() }
                    .align(Alignment.End)
                    .padding(8.dp)
            )
            Text(text = "Añadir Empleado", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.size(8.dp))
            OutlinedTextField(
                value = nombre,
                onValueChange = { onNameChange(it) },
                label = { Text(text = "Nombre") },
                maxLines = 1,
                singleLine = true
            )
            Spacer(modifier = Modifier.size(6.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { onEmailChange(it) },
                label = { Text(text = "Email") },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )
            Spacer(modifier = Modifier.size(12.dp))
            Button(
                onClick = { onClickAgregar() }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp), enabled = validarEmail
            ) {
                Text(text = "Añadir", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.size(12.dp))
        }

    }
}