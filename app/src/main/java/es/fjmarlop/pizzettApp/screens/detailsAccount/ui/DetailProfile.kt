package es.fjmarlop.pizzettApp.screens.detailsAccount.ui

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
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Rule
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import es.fjmarlop.pizzeta.R

@Composable
fun DetailsProfileScreen(
    detailProfileViewModel: DetailProfileViewModel,
    navHostController: NavHostController
) {

    LaunchedEffect(true) {
        detailProfileViewModel.getUser()
    }

    val user by detailProfileViewModel.user.observeAsState()

    val name by detailProfileViewModel.name.collectAsState()
    val phone by detailProfileViewModel.phone.collectAsState()

    val isNameEditable by detailProfileViewModel.isNameEditable.collectAsState()
    val isPhoneEditable by detailProfileViewModel.isPhoneEditable.collectAsState()



    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            IconButton(onClick = { detailProfileViewModel.goBack(navHostController) }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
            }
        }
        Box(Modifier.padding(horizontal = 32.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                DetailProfileHeader()

                user?.let { user ->

                    DetailsProfileEmail(value = user.email)
                    Spacer(modifier = Modifier.size(12.dp))
                    DetailsProfileName(
                        value = user.name,
                        show = isNameEditable,
                        onClickEdit = { detailProfileViewModel.onClickNameEdit() })
                    ProfileNameEdit(
                        value = name,
                        show = isNameEditable,
                        onClickEdit = { detailProfileViewModel.onClickNameEdit() },
                        onTextNameChange = { detailProfileViewModel.onTextNameChange(it) },
                        onClickSave = {
                            detailProfileViewModel.updateUserName(
                                name,
                                user.email,
                                navHostController
                            )
                        })
                    Spacer(modifier = Modifier.size(12.dp))
                    DetailsProfilePhone(
                        value = user.phone,
                        show = isPhoneEditable,
                        onClickEdit = { detailProfileViewModel.onClickPhoneEdit() })
                    ProfilePhoneEdit(
                        value = phone,
                        show = isPhoneEditable,
                        onClickEdit = { detailProfileViewModel.onClickPhoneEdit() },
                        onTextPhoneChange = { detailProfileViewModel.onTextPhoneChange(it) },
                        onClickSave = {
                            detailProfileViewModel.updatePhone(
                                phone,
                                user.email,
                                navHostController
                            )
                        })
                    Spacer(modifier = Modifier.size(12.dp))
                    ProfileDetailPrivacy()
                    Spacer(modifier = Modifier.size(12.dp))
                    ProfileDetailTerms()
                }
            }
        }
    }
}

@Composable
fun DetailProfileHeader() {
    Text(
        text = "Detalles de tu cuenta",
        fontFamily = FontFamily(Font(R.font.roboto_condensed)),
        fontSize = 32.sp,
        modifier = Modifier.padding(vertical = 16.dp)
    )
}

@Composable
fun DetailsProfileEmail(value: String) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Correo electrónico") },
        value = value,
        onValueChange = {},
        enabled = false,
    )
}

@Composable
fun DetailsProfileName(value: String, onClickEdit: () -> Unit, show: Boolean) {
    if (!show) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Nombre") },
            value = value,
            onValueChange = {},
            enabled = false,
            trailingIcon = {
                IconButton(onClick = { onClickEdit() }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "edit")
                }
            }
        )
    }
}

@Composable
fun ProfileNameEdit(
    value: String,
    show: Boolean,
    onClickEdit: () -> Unit,
    onTextNameChange: (String) -> Unit,
    onClickSave: () -> Unit
) {
    if (show) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = { onTextNameChange(it) },
            label = { Text(text = "Nombre") },
            trailingIcon = {
                Row {
                    IconButton(onClick = { onClickSave() }) {
                        Icon(imageVector = Icons.Default.Save, contentDescription = "save")
                    }
                    IconButton(onClick = { onClickEdit() }) {
                        Icon(imageVector = Icons.Default.Cancel, contentDescription = "cancel")
                    }
                }
            })
    }
}

@Composable
fun DetailsProfilePhone(value: String, show: Boolean, onClickEdit: () -> Unit) {
    if (!show) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Teléfono") },
            value = value,
            onValueChange = {},
            enabled = false,
            trailingIcon = {
                IconButton(onClick = { onClickEdit() }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "edit")
                }
            }
        )
    }
}

@Composable
fun ProfilePhoneEdit(
    value: String,
    show: Boolean,
    onClickEdit: () -> Unit,
    onTextPhoneChange: (String) -> Unit,
    onClickSave: () -> Unit
) {
    if (show) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = { onTextPhoneChange(it) },
            label = { Text(text = "Teléfono") },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            trailingIcon = {
                Row {
                    IconButton(onClick = { onClickSave() }) {
                        Icon(imageVector = Icons.Default.Save, contentDescription = "save")
                    }
                    IconButton(onClick = { onClickEdit() }) {
                        Icon(imageVector = Icons.Default.Cancel, contentDescription = "cancel")
                    }
                }

            })
    }
}

@Composable
fun ProfileDetailPrivacy() {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = Icons.Default.PrivacyTip, contentDescription = "privacity")
        Text(
            text = "Aceptar política de privacidad",
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )
        Switch(checked = true, onCheckedChange = {})
    }
}

@Composable
fun ProfileDetailTerms() {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = Icons.Default.Rule, contentDescription = "Terms Use")
        Text(
            text = "Aceptar terminos de uso",
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )
        Switch(checked = true, onCheckedChange = {})
    }
}

