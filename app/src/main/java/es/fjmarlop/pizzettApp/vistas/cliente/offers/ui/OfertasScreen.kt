package es.fjmarlop.pizzettApp.vistas.cliente.offers.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import es.fjmarlop.pizzeta.R
import es.fjmarlop.pizzettApp.vistas.cliente.main.ui.MainScafold
import es.fjmarlop.pizzettApp.vistas.cliente.main.ui.MainViewModel

@Composable
fun OfertasScreen(
    mainViewModel: MainViewModel,
    navHostController: NavHostController
) {
    MainScafold(content = { vistaOfertas() }, navHostController = navHostController, mainViewModel)
}


@Composable
fun vistaOfertas() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState, enabled = true),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "Conoce nuestras ofertas",
            fontFamily = FontFamily(Font(R.font.roboto_condensed)),
            textAlign = TextAlign.Center,
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(16.dp))
        OfertaCompleta(
            oferta = "2x1",
            ofertaLinea2 = null,
            tamanioText = 150,
            imagenRes = R.drawable.of1,
            condiciones = "Lunes y Martes. Sólo LOCAL y RECOGER",
            condicionesLinea2 = null
        )
        OfertaCompleta(
            oferta = "3x2",
            ofertaLinea2 = "online",
            tamanioText = 100,
            imagenRes = R.drawable.of2,
            condiciones = "Miércoles. Sólo ONLINE",
            condicionesLinea2 = null
        )
        OfertaCompleta(
            oferta = "Refresco",
            ofertaLinea2 = "gratis",
            tamanioText = 90,
            imagenRes = R.drawable.of3,
            condiciones = "Jueves. Sólo LOCAL y RECOGER",
            condicionesLinea2 = null
        )
        OfertaCompleta(
            oferta = "Ensalada",
            ofertaLinea2 = "gratis",
            tamanioText = 90,
            imagenRes = R.drawable.of4,
            condiciones = "Domingo. Pedido mín. 2 pizzas.",
            condicionesLinea2 = "Sólo LOCAL y RECOGER"
        )
        OfertaCompleta(
            oferta = "Envio",
            ofertaLinea2 = "gratis",
            tamanioText = 95,
            imagenRes = R.drawable.of5,
            condiciones = "Domingo. Sólo ONLINE",
            condicionesLinea2 = null
        )
        Spacer(modifier = Modifier.size(32.dp))
    }
}

@Composable
fun OfertaCompleta(
    oferta: String,
    ofertaLinea2: String?,
    tamanioText: Int,
    @DrawableRes imagenRes: Int,
    condiciones: String,
    condicionesLinea2: String?
) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

        ItemOferta(
            oferta = oferta,
            ofertaLinea2 = ofertaLinea2,
            tamanioText = tamanioText,
            imagenRes = imagenRes
        )
        CondicionesOferta(condiciones = condiciones, condicionesLinea2 = condicionesLinea2)
        Spacer(modifier = Modifier.size(12.dp))
    }
}

@Composable
fun CondicionesOferta(condiciones: String, condicionesLinea2: String?) {
    Text(text = condiciones)
    if (condicionesLinea2 != null) {
        Text(text = condicionesLinea2)
    }
    Text(text = "(Excepto visperas y festivos)", fontSize = 12.sp)
}


@Composable
fun ItemOferta(
    oferta: String,
    ofertaLinea2: String?,
    tamanioText: Int,
    @DrawableRes imagenRes: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .height(250.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imagenRes),
            contentDescription = "Oferta",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                // .height(220.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
        )
        Column(
            modifier= Modifier.height((IntrinsicSize.Min)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextWithShadow(text = oferta,  tamanioText = tamanioText)
            if (ofertaLinea2 != null) {
                TextWithShadow(text = ofertaLinea2,  tamanioText = tamanioText)
            }
        }
    }
}

@Composable
fun TextWithShadow(
    text: String,
    tamanioText: Int
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.height((IntrinsicSize.Min))) {

        Text(
            text = text,
            color = Color.Black,
            modifier = Modifier
                .offset(
                    x = 5.dp,
                    y = 3.dp
                )
                .alpha(0.75f)
                .height((IntrinsicSize.Min)),
            fontFamily = FontFamily(Font(R.font.rashavine)),
            fontSize = tamanioText.sp,
            letterSpacing = 2.sp
        )
        Text(
            text = text,
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.rashavine)),
            fontSize = tamanioText.sp,
            modifier = Modifier.height((IntrinsicSize.Min)),
            letterSpacing = 2.sp
        )
    }
}

