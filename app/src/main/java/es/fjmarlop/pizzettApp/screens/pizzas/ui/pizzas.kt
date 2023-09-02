package es.fjmarlop.pizzettApp.screens.pizzas.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import es.fjmarlop.pizzeta.R
import es.fjmarlop.pizzettApp.models.PizzaModel

@Composable
fun PizzasScreen(pizzasViewModel: PizzasViewModel, navHostController: NavHostController){

    LaunchedEffect(true ){
        pizzasViewModel.getAllPizzas()
    }

    val lista = pizzasViewModel.pizzasList

    Text(text = "Nuestras pizzas")

    LazyColumn{
        items(lista){ pizza ->
            PizzaItem(pizza = pizza)
        }
    }
}
@Composable
fun PizzaItem(pizza: PizzaModel) {

    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_la_pizzetta),
            contentDescription = null,
            modifier = Modifier.size(60.dp)
        )
        Text(
            text = pizza.pizzaName, modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )
    }

}