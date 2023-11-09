package es.fjmarlop.pizzettApp.vistas.cliente.main.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Wysiwyg
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import es.fjmarlop.pizzeta.R
import es.fjmarlop.pizzettApp.dataBase.Remote.retrofit.models.ProductoModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    navHostController: NavHostController
) {

    LaunchedEffect(true) {
        mainViewModel.addUser()
        mainViewModel.resetIndex()
    }

    MainScafold(
        content = { VistaHome(mainViewModel) },
        navHostController = navHostController,
        mainViewModel
    )
}

@Composable
fun VistaHome(
    mainViewModel: MainViewModel
) {

    val user by mainViewModel.user.observeAsState()
    val categoria by mainViewModel.categoria.collectAsState()
    val list by mainViewModel.productsList.collectAsState()
    val activateButtonAddLine by mainViewModel.activateButtonAddLine.collectAsState()
    val recomendados by mainViewModel.productsListForRandom.collectAsState()
    val showRecomendados by mainViewModel.showRecomendados.collectAsState()

    LaunchedEffect(true) {
        delay(500)
        mainViewModel.getUser()
    }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {

        user?.let {
            TextWelcome(
                nombre = if (it.name.isEmpty()) mainViewModel.obtenerUsername(it.email) else mainViewModel.obtenerUsername(
                    it.name
                )
            )
        }

        TitleCarrusel(string = "¿Qué te apetece comer hoy?")
        DividerMain()
        Carrusel(mainViewModel)

        Spacer(modifier = Modifier.size(24.dp))
        TitleCarrusel(string = categoria)

        DividerMain()

        ProductList(
            list = list,
            mainViewModel = mainViewModel,
            activar = activateButtonAddLine
        )

        /**
         * Cuando iniciamos sesión por primera vez, la creación de la vista es mas rápida
         * que la petición a la API por lo tanto la pantalla se inicia con la lista de recomendados vacía.
         * Para evitar que la vista aparezca en blanco, hago la petición a la API
         * mas tarde para intentar retardar la petición.
         * */

        if (recomendados.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            LaunchedEffect(true) {
                delay(1500)
                mainViewModel.getProductosParaRecomendados()
            }
        } else {
            RecomendadosList(
                show = showRecomendados,
                list = recomendados,
                mainViewModel,
                activateButtonAddLine
            )
        }
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
    Divider(
        Modifier.padding(vertical = 6.dp),
        color = MaterialTheme.colorScheme.primary,
        thickness = 2.dp
    )
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
fun Carrusel(mainViewModel: MainViewModel) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState, enabled = true)
            .clip(RoundedCornerShape(20.dp))
    )
    {
        ItemCarrusel("Ensaladas", imagen = R.drawable.ensalada) {
            mainViewModel.onClickCategoria("Ensaladas")
        }
        ItemCarrusel("Pizzas", imagen = R.drawable.pizzas) {
            mainViewModel.onClickCategoria("Pizzas")
        }
        ItemCarrusel("Pastas", imagen = R.drawable.pastas) {
            mainViewModel.onClickCategoria("Pastas")
        }
        ItemCarrusel("Gratinados", imagen = R.drawable.gratinados) {
            mainViewModel.onClickCategoria("Gratinados")
        }
        ItemCarrusel("Postres", imagen = R.drawable.postres) {
            mainViewModel.onClickCategoria("Postres")
        }
        ItemCarrusel("Bebidas", imagen = R.drawable.bebidas) {
            mainViewModel.onClickCategoria("Bebidas")
        }
    }
}

@Composable
fun ItemCarrusel(plato: String, @DrawableRes imagen: Int, onClickItem: () -> Unit) {
    Column(Modifier.clickable { onClickItem() }) {
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
            .width(80.dp)
            .height(80.dp),
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

    val index by mainViewModel.index.collectAsState()
    val lineasTotales by mainViewModel.lineasTotal.collectAsState()

    Scaffold(

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
                cantidadLineas = lineasTotales
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
fun BottomBar(
    index: Int,
    onClickInicio: () -> Unit,
    onClickOfertas: () -> Unit,
    onClickCarrito: () -> Unit,
    onClickMisPedidos: () -> Unit,
    onClickCuenta: () -> Unit,
    cantidadLineas: Int
) {

    NavigationBar(containerColor = Color.Transparent) {
        NavigationBarItem(colors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.inverseOnSurface
        ), selected = index == 0, onClick = { onClickInicio() }, icon = {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Inicio"
            )
        }, label = { Text(text = "Inicio") })
        NavigationBarItem(colors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.inverseOnSurface
        ), selected = index == 1, onClick = { onClickOfertas() }, icon = {
            Icon(
                imageVector = Icons.Default.Percent,
                contentDescription = "Ofertas"
            )
        }, label = { Text(text = "Ofertas") })
        NavigationBarItem(colors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.inverseOnSurface
        ), selected = index == 2, onClick = { onClickCarrito() }, icon = {

            BadgedBox(
                badge = {
                    if (cantidadLineas > 0)
                        Badge { Text(text = cantidadLineas.toString()) }
                }, modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    Icons.Filled.ShoppingCart,
                    contentDescription = "shoppingCart"
                )
            }

        }, label = { Text(text = "Compra") })
        NavigationBarItem(colors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.inverseOnSurface
        ), selected = index == 3, onClick = { onClickMisPedidos() }, icon = {
            Icon(
                imageVector = Icons.Default.Wysiwyg,
                contentDescription = "Mis Pedidos"
            )
        }, label = { Text(text = "Pedidos") })
        NavigationBarItem(colors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.inverseOnSurface
        ), selected = index == 4, onClick = { onClickCuenta() }, icon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Cuenta"
            )
        }, label = { Text(text = "Cuenta") })
    }
}

@Composable
fun RecomendadosList(
    show: Boolean,
    list: List<ProductoModel>,
    mainViewModel: MainViewModel,
    activar: Boolean
) {

    if (show) {
        LazyColumn {
            items(list) { producto -> RecomendadoItem(producto = producto, mainViewModel, activar) }
        }
    }
}

@Composable
fun RecomendadoItem(producto: ProductoModel, mainViewModel: MainViewModel, activar: Boolean) {

    val img = rememberAsyncImagePainter(producto.imagen_producto)
    val ing = producto.ingredients.sortedBy { it.id }.joinToString(", ") { it.ingredientName }
    var isSheetOpen by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable {
                isSheetOpen = true
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = img, contentDescription = null, modifier = Modifier
                    .width(120.dp)
                    .height(120.dp)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Fit
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(
                    text = producto.nombre_producto,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                if (producto.categoria.joinToString { it.nombre_categoria }.contains("Pizzas")) {
                    Text(
                        text = producto.descripcion + ing,
                        fontSize = 10.sp,
                        lineHeight = 12.sp,
                        textAlign = TextAlign.Justify
                    )
                } else {
                    Text(
                        text = producto.descripcion,
                        fontSize = 10.sp,
                        lineHeight = 12.sp,
                        textAlign = TextAlign.Justify
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.size(8.dp))

    if (isSheetOpen) {
        ItemSheet(
            onDismiss = { isSheetOpen = false },
            producto = producto,
            mainViewModel = mainViewModel,
            activar = activar
        )
    }
}

@Composable
fun ProductList(
    list: List<ProductoModel>,
    mainViewModel: MainViewModel,
    activar: Boolean
) {

    LazyColumn {
        items(list) { producto ->
            ProductItem(producto = producto, mainViewModel = mainViewModel, activar = activar)
        }
    }

}


@Composable
fun ProductItem(producto: ProductoModel, mainViewModel: MainViewModel, activar: Boolean) {

    Spacer(modifier = Modifier.size(12.dp))
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        ITemBody(
            producto = producto,
            mainViewModel = mainViewModel,
            activar = activar,
            modifier = Modifier.weight(1f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ITemBody(
    producto: ProductoModel,
    mainViewModel: MainViewModel,
    activar: Boolean,
    modifier: Modifier
) {

    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val ingredients =
        producto.ingredients.sortedBy { it.id }.joinToString(", ") { it.ingredientName }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                isSheetOpen = true
                scope.launch { sheetState.isVisible }
            }

    ) {
        Text(
            text = producto.nombre_producto,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.size(4.dp))

        Text(
            text = ingredients,
            fontSize = 12.sp,
            color = Color(0xFFA93700),
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Justify
        )
        Spacer(modifier = Modifier.size(12.dp))
        Divider()
    }
    if (isSheetOpen) {
        ItemSheet(
            onDismiss = { isSheetOpen = false },
            producto = producto,
            mainViewModel = mainViewModel,
            activar = activar
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemSheet(
    onDismiss: () -> Unit,
    producto: ProductoModel,
    mainViewModel: MainViewModel,
    activar: Boolean
) {

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        dragHandle = { }) {

        LaunchedEffect(true) {
            mainViewModel.resetValues()
        }
        Box(Modifier.fillMaxSize()) {
            Column {
                SheetImagen(producto = producto)
                Spacer(modifier = Modifier.size(18.dp))
                SheetText(producto = producto)
                Spacer(modifier = Modifier.size(18.dp))
                Divider()
                SheetTamanoPvp(producto = producto, mainViewModel = mainViewModel)
                Divider()
                Spacer(modifier = Modifier.size(18.dp))
                SheetCantidad(mainViewModel = mainViewModel)
                OutlinedButton(
                    enabled = activar,
                    onClick = {
                        mainViewModel.addOrderLine(producto)
                        onDismiss()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    Text(text = "Añadir al pedido")
                }
                Spacer(modifier = Modifier.size(70.dp))
            }
        }
    }
}


@Composable
fun SheetImagen(producto: ProductoModel) {
    val img = rememberAsyncImagePainter(producto.imagen_producto)

    if (producto.nombre_producto.isNotBlank()) {
        Image(
            painter = img,
            contentDescription = null, modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(), contentScale = ContentScale.Crop
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.logo_la_pizzetta),
            contentDescription = null,
            modifier = Modifier
                .height(250.dp)
        )
    }
}

@Composable
fun SheetText(producto: ProductoModel) {

    val ingredients = producto.ingredients.sortedBy { it.id }
        .joinToString(", ") { it.ingredientName }
    val categoria = producto.categoria.joinToString { it.nombre_categoria }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = producto.nombre_producto,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.size(14.dp))

        if (categoria == "Pizzas") {
            Text(
                text = producto.descripcion + ingredients,
                fontSize = 18.sp,
                color = Color(0xFFA93700),
                modifier = Modifier.padding(horizontal = 24.dp),
                textAlign = TextAlign.Justify
            )
        } else {
            Text(
                text = producto.descripcion,
                fontSize = 14.sp,
                color = Color(0xFFA93700),
                modifier = Modifier.padding(horizontal = 24.dp),
                textAlign = TextAlign.Justify
            )

        }
    }
}

@Composable
fun SheetTamanoPvp(producto: ProductoModel, mainViewModel: MainViewModel) {

    var tamanoSelected by remember { mutableIntStateOf(0) }

    val list = producto.tamanios.sortedBy { it.pvp }

    list.forEach { item ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(horizontal = 18.dp)
        ) {
            RadioButton(
                selected = tamanoSelected == item.id,
                onClick = {
                    tamanoSelected = item.id
                    /* TODO FALTA IMPLEMENTAR GUARDAR ELECCION */
                    mainViewModel.onTamanoSelected(item)
                })
            Text(text = "Tamaño ${item.tamano}, pvp: ${item.pvp} €")
        }
    }
}

@Composable
fun SheetCantidad(mainViewModel: MainViewModel) {

    val cantidad by mainViewModel.cantidad.collectAsState()

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Cantidad",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { mainViewModel.restarCantidad() }, enabled = cantidad > 0) {
                Icon(imageVector = Icons.Default.Remove, contentDescription = null)
            }

            Text(
                text = cantidad.toString(),
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            IconButton(onClick = { mainViewModel.aumentarCantidad() }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    }
}