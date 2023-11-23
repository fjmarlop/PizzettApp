package es.fjmarlop.pizzettApp.vistas.gestion.mainGestion.ui

import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import es.fjmarlop.pizzeta.R
import es.fjmarlop.pizzettApp.vistas.login.domain.googleLogin.GoogleAuthUiClient

@Composable
fun MainGestionScreen(mainGestionViewModel: MainGestionViewModel,googleAuthUiClient: GoogleAuthUiClient, navHostController: NavHostController) {

    val nombreEmpleado by mainGestionViewModel.nombreEmpleado.collectAsState()
    val emailEmpleado by mainGestionViewModel.emailEmpleado.collectAsState()
    val validarEmail by mainGestionViewModel.validarEmail.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Pantalla principal de gestion",
                modifier = Modifier.padding(dimensionResource(id = R.dimen.margen)),
                style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(12.dp))
            Productos()
            Spacer(modifier = Modifier.size(8.dp))
            Pedidos()
            Spacer(modifier = Modifier.size(8.dp))
            Ofertas()
            Spacer(modifier = Modifier.size(8.dp))
            Empleados(
                onNameChange = { mainGestionViewModel.onTextEmpleadoChange(it, emailEmpleado) },
                onEmailChange = { mainGestionViewModel.onTextEmpleadoChange(nombreEmpleado, it) },
                nombre = nombreEmpleado,
                email = emailEmpleado,
                validarEmail = validarEmail,
                onClickAgregar = { /* TODO */ }
            )
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.BottomEnd) {
                Button(
                    onClick = { mainGestionViewModel.cerrarSesion(googleAuthUiClient, navHostController) },
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.margen))
                        .fillMaxWidth()
                ) {
                    Text(text = "Cerrar Sesion", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
        }
    }
}


@Composable
fun Empleados(
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    nombre: String, email: String,
    validarEmail: Boolean,
    onClickAgregar: () -> Unit,
) {

    var showAdd by remember { mutableStateOf(false) }

    Column {
        Text(
            text = "Empleados",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.margen))
        )
        BarraIconos(
            onClickAgregar = { showAdd = !showAdd },
            onClickBorrar = { /*TODO*/ },
            onClickActualizar = { /*TODO*/ },
            onClickListar = { /*TODO*/ })
    }
    AddEmpleado(
        show = showAdd,
        onDismissRequest = { showAdd = false },
        onNameChange = { onNameChange(it) },
        onEmailChange = { onEmailChange(it) },
        nombre = nombre,
        email = email,
        validarEmail = validarEmail,
        onClickAgregar = {
            onClickAgregar()
            showAdd = false
        }
    )

}


@Composable
fun Ofertas() {
    Column {
        Text(
            text = "Ofertas",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.margen))
        )
        BarraIconos(
            onClickAgregar = { /*TODO*/ },
            onClickBorrar = { /*TODO*/ },
            onClickActualizar = { /*TODO*/ },
            onClickListar = { /*TODO*/ })
    }
}


@Composable
fun Pedidos() {
    Column {
        Text(
            text = "Pedidos",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.margen))
        )
        BarraIconos(
            onClickAgregar = { /*TODO*/ },
            onClickBorrar = { /*TODO*/ },
            onClickActualizar = { /*TODO*/ },
            onClickListar = { /*TODO*/ })
    }
}

@Composable
fun Productos() {
    Column {
        Text(
            text = "Productos",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.margen))
        )
        BarraIconos(
            onClickAgregar = { /*TODO*/ },
            onClickBorrar = { /*TODO*/ },
            onClickActualizar = { /*TODO*/ },
            onClickListar = { /*TODO*/ })
    }
}

@Composable
fun BarraIconos(
    onClickAgregar: () -> Unit,
    onClickBorrar: () -> Unit,
    onClickActualizar: () -> Unit,
    onClickListar: () -> Unit
) {
    Row(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.margen))) {
        IconButton(onClick = { onClickAgregar() }, modifier = Modifier.weight(1f)) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
        }
        IconButton(onClick = { onClickActualizar() }, modifier = Modifier.weight(1f)) {
            Icon(
                imageVector = Icons.Default.Update,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
        }
        IconButton(onClick = { onClickBorrar() }, modifier = Modifier.weight(1f)) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
        }
        IconButton(onClick = { onClickListar() }, modifier = Modifier.weight(1f)) {
            Icon(
                imageVector = Icons.Default.ViewList,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }
    Divider(modifier = Modifier.padding(dimensionResource(id = R.dimen.margen)))
}


@Composable
fun AddEmpleado(
    show: Boolean,
    onDismissRequest: () -> Unit,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    nombre: String, email: String,
    validarEmail: Boolean,
    onClickAgregar: () -> Unit
) {

    if (show) {
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
}