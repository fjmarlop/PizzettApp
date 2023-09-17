package es.fjmarlop.pizzettApp.screens.createAccount.ui

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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
fun CreateAccountScreen(
    createAccountViewModel: CreateAccountViewModel,
    navController: NavHostController
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            IconButton(onClick = { createAccountViewModel.goToBack(navController) }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
            }
        }
        Titulo()
        BodyForm(createAccountViewModel = createAccountViewModel, navController)
    }
}

@Composable
fun Titulo() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.logo_la_pizzetta),
            contentDescription = "Logo Pizzetta"
        )
        Text(
            text = "Crear cuenta",
            fontFamily = FontFamily(Font(R.font.roboto_condensed)),
            fontSize = 32.sp
        )
    }
}

@Composable
fun BodyForm(createAccountViewModel: CreateAccountViewModel, navController: NavHostController) {

    val usuario: String by createAccountViewModel.usuario.observeAsState(initial = "")
    val password: String by createAccountViewModel.password.observeAsState(initial = "")
    val replyPassword: String by createAccountViewModel.replyPassword.observeAsState(initial = "")

    val isEmailValido: Boolean by createAccountViewModel.isEmailValido.observeAsState(initial = true)
    val isPasswordValido: Boolean by createAccountViewModel.isPasswordValida.observeAsState(initial = true)
    val isReplyPasswordValido: Boolean
            by createAccountViewModel.isReplyPasswordValida.observeAsState(initial = true)

    val isCreateEnable: Boolean by createAccountViewModel.isCreateEnable.observeAsState(initial = false)

    Column(Modifier.padding(horizontal = 32.dp)) {
        Spacer(Modifier.size(16.dp))
        UserCreate(
            user = usuario,
            isValid = isEmailValido,
            onTextChanged = { createAccountViewModel.onCreateEmailChanged(it) })
        Spacer(modifier = Modifier.size(8.dp))
        PasswordCreate(
            password = password,
            isValido = isPasswordValido,
            onTextChanged = { createAccountViewModel.onCreatePassworChanged(it) })
        ReplyPasswordCreate(
            password = replyPassword,
            onTextChanged = { createAccountViewModel.onCreateReplyPasswordChanged(it) },
            isValido = isReplyPasswordValido
        )

        CreateButton(
            isEnable = isCreateEnable,
            registroViewModel = createAccountViewModel,
            navController = navController
        )

    }

}

@Composable
fun CreateButton(
    isEnable: Boolean,
    registroViewModel: CreateAccountViewModel,
    navController: NavHostController
) {
    Button(
        onClick = { registroViewModel.createAccount(navController) },
        enabled = isEnable,
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Text(text = "Crear cuenta")
    }
}

@Composable
fun UserCreate(user: String, isValid: Boolean, onTextChanged: (String) -> Unit) {

    OutlinedTextField(
        value = user,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth(),
        label = { Text(text = "Correo electrónico")},
        placeholder = { Text(text = "Introduce tu dirección correo") },
        maxLines = 1,
        singleLine = true,
        isError = !isValid,
        supportingText = {
            if (!isValid) Text(text = "Error, el email no es válido")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
            cursorColor = MaterialTheme.colorScheme.tertiary,
            focusedLabelColor = MaterialTheme.colorScheme.tertiary,
        )
    )

}

@Composable
fun PasswordCreate(password: String, onTextChanged: (String) -> Unit, isValido: Boolean) {
    var passwordVisibility by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth(),
        label = { Text(text = "Contraseña")},
        placeholder = { Text("Introduce tu contraseña") },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
            cursorColor = MaterialTheme.colorScheme.tertiary,
            focusedLabelColor = MaterialTheme.colorScheme.tertiary,
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
    OutlinedTextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth(),
        label = { Text(text = "Contraseña")},
        placeholder = { Text("Repite tu contraseña") },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
            cursorColor = MaterialTheme.colorScheme.tertiary,
            focusedLabelColor = MaterialTheme.colorScheme.tertiary,
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