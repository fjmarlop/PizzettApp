package es.fjmarlop.pizzettApp.views.crearCuentaView.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import es.fjmarlop.pizzeta.R

@Composable
fun CrearCuentaScreen(
    crearCuentaViewModel: CrearCuentaViewModel,
    navController: NavHostController
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.logo_la_pizzetta), contentDescription = "Logo Pizzetta", modifier = Modifier.padding(16.dp))
        Titulo()
        BodyForm(crearCuentaViewModel = crearCuentaViewModel, navController)
    }
}

@Composable
fun Titulo() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Crear cuenta",
            fontFamily = FontFamily(Font(R.font.roboto_condensed)),
            fontSize = 32.sp
        )
    }
}

@Composable
fun BodyForm(crearCuentaViewModel: CrearCuentaViewModel, navController: NavHostController) {

    val usuario: String by crearCuentaViewModel.usuario.observeAsState(initial = "")
    val password: String by crearCuentaViewModel.password.observeAsState(initial = "")
    val replyPassword: String by crearCuentaViewModel.replyPassword.observeAsState(initial = "")

    val isEmailValido: Boolean by crearCuentaViewModel.isEmailValido.observeAsState(initial = true)
    val isPasswordValido: Boolean by crearCuentaViewModel.isPasswordValida.observeAsState(initial = true)
    val isReplyPasswordValido: Boolean
            by crearCuentaViewModel.isReplyPasswordValida.observeAsState(initial = true)

    val isCreateEnable: Boolean by crearCuentaViewModel.isCreateEnable.observeAsState(initial = false)

    Column() {
        Spacer(Modifier.size(16.dp))
        UsuarioCreate(
            usuario = usuario,
            isValido = isEmailValido,
            onTextChanged = { crearCuentaViewModel.onCreateEmailChanged(it) })
        Spacer(modifier = Modifier.size(8.dp))
        PasswordCreate(
            password = password,
            isValido = isPasswordValido,
            onTextChanged = { crearCuentaViewModel.onCreatePassworChanged(it) })
        ReplyPasswordCreate(
            password = replyPassword,
            onTextChanged = { crearCuentaViewModel.onCreateReplyPasswordChanged(it) },
            isValido = isReplyPasswordValido
        )

        CreateButton(
            isEnable = isCreateEnable,
            registroViewModel = crearCuentaViewModel,
            navController = navController
        )
    }

}

@Composable
fun CreateButton(
    isEnable: Boolean,
    registroViewModel: CrearCuentaViewModel,
    navController: NavHostController
) {
    Button(
        onClick = { registroViewModel.createAccount(navController) },
        enabled = isEnable,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Crear cuenta")
    }
}

@Composable
fun UsuarioCreate(usuario: String, isValido: Boolean, onTextChanged: (String) -> Unit) {

    TextField(
        value = usuario,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp)),
        placeholder = { Text(text = "Email") },
        maxLines = 1,
        singleLine = true,
        isError = !isValido,
        supportingText = {
            if (!isValido) Text(text = "Error, el email no es válido")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0x0847BB02),
            focusedContainerColor = Color(0x0847BB02),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorContainerColor = Color(0xFFf5CBCB),
        )
    )

}

@Composable
fun PasswordCreate(password: String, onTextChanged: (String) -> Unit, isValido: Boolean) {
    var passwordVisibility by remember { mutableStateOf(false) }
    TextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp)),
        placeholder = { Text("Contraseña") },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0x0847BB02),
            focusedContainerColor = Color(0x0847BB02),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorContainerColor = Color(0xFFf5CBCB),
        ),
        singleLine = true,
        maxLines = 1,
        isError = !isValido,
        supportingText = {
            if (!isValido) Text(text = "Introduce al menos 6 caracteres, 1 mayúscula y 1 número")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val imagen = if (passwordVisibility) {
                Icons.Filled.VisibilityOff
            } else {
                Icons.Filled.Visibility
            }
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(imageVector = imagen, contentDescription = "show password")
            }
        },
        visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }
    )
}

@Composable
fun ReplyPasswordCreate(password: String, onTextChanged: (String) -> Unit, isValido: Boolean) {
    var passwordVisibility by remember { mutableStateOf(false) }
    TextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp)),
        placeholder = { Text("Repita la contraseña") },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0x0847BB02),
            focusedContainerColor = Color(0x0847BB02),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorContainerColor = Color(0xFFf5CBCB),
        ),
        singleLine = true,
        maxLines = 1,
        isError = !isValido,
        supportingText = {
            if (!isValido) Text(text = "La contraseña no coincide")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val imagen = if (passwordVisibility) {
                Icons.Filled.VisibilityOff
            } else {
                Icons.Filled.Visibility
            }
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(imageVector = imagen, contentDescription = "show password")
            }
        },
        visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }
    )
}