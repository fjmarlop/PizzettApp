package es.fjmarlop.pizzettApp.screens.address.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.fjmarlop.pizzeta.R
import es.fjmarlop.pizzettApp.models.AddressModel

@Composable
fun AddressScreen(addressViewModel: AddressViewModel, navHostController: NavHostController) {

    val email = Firebase.auth.currentUser?.email

    LaunchedEffect(true) {
        if (email != null) {
            addressViewModel.getAddressList(email)
        }
    }

    val addressList by addressViewModel.addressList.collectAsState()

    val addressName by addressViewModel.addressName.collectAsState()
    val addressAddress by addressViewModel.addressAddress.collectAsState()
    val addressCity by addressViewModel.addressCity.collectAsState()
    val addressCodPostal by addressViewModel.addressCodPostal.collectAsState()
    val addressId by addressViewModel.addressId.collectAsState()

    val isSaveEnable by addressViewModel.isSaveEnable.collectAsState()

    var showAddAddres by remember { mutableStateOf(false) }

    var update by remember { mutableStateOf(false) }
    var delete by remember { mutableStateOf(false) }



    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                IconButton(onClick = { addressViewModel.goBack(navHostController) }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "close")
                }
            }
            Column(
                Modifier.padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Tu libreta de direcciones",
                    fontFamily = FontFamily(Font(R.font.roboto_condensed)),
                    fontSize = 24.sp, textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(20.dp))
                AddressListItem(lista = addressList,
                    delete = delete,
                    onLongPress = {
                        delete = !delete
                    },
                    onClickDelete = {
                                    addressViewModel.deleteAddress(it.id)
                    },
                    onClickEdit = {
                        showAddAddres = true
                        update = true
                        addressViewModel.recuperarDatos(
                            it.id,
                            it.name,
                            it.address,
                            it.city,
                            it.codPostal
                        )
                    })
            }
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
                FloatingActionButton(
                    onClick = {
                        showAddAddres = true
                        update = false
                        addressViewModel.reset()
                    },
                    modifier = Modifier.padding(32.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "add address")
                }
            }
            if (showAddAddres) {
                Dialog(
                    onDismissRequest = { showAddAddres = false },
                ) {
                    AddDialogContent(
                        name = addressName,
                        address = addressAddress,
                        city = addressCity,
                        codPostal = addressCodPostal,
                        isSaveEnable = isSaveEnable,
                        onClickClose = { addressViewModel.close(navHostController) },
                        onClickSave = {
                            addressViewModel.addAddress(
                                AddressModel(
                                    addressId,
                                    addressName,
                                    addressAddress,
                                    addressCity,
                                    addressCodPostal,
                                    email ?: ""
                                )
                            )
                            showAddAddres = false
                        },
                        addressViewModel = addressViewModel,
                        update = update,
                        onClickUpdate = {
                            addressViewModel.updateAddress(
                                addressId,
                                addressName,
                                addressAddress,
                                addressCity,
                                addressCodPostal
                            )
                            showAddAddres = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AddressListItem(
    lista: List<AddressModel>,
    onClickEdit: (AddressModel) -> Unit,
    onLongPress: () -> Unit,
    delete: Boolean,
    onClickDelete: (AddressModel) -> Unit
) {

    LazyColumn {
        items(lista) { address ->
            AddressItem(
                addressModel = address,
                onClickEdit = { onClickEdit(address) },
                onLongPress = { onLongPress() },
                delete = delete,
                onClickDelete = { onClickDelete(address) }
            )
        }
    }
}

@Composable
fun AddressItem(
    addressModel: AddressModel,
    onClickEdit: () -> Unit,
    onLongPress: () -> Unit,
    delete: Boolean,
    onClickDelete: () -> Unit
) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = { onLongPress() })
            }, verticalAlignment = Alignment.CenterVertically
    ) {
        if (delete) {
            Box(modifier = Modifier.padding(3.dp)) {
                IconButton(onClick = { onClickDelete() }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "delete",
                        tint = Color.Red
                    )
                }
            }
        }
        Column(modifier = Modifier
            .weight(1f)
            .padding(horizontal = 8.dp)) {
            Text(text = addressModel.name, fontWeight = FontWeight.Bold)
            Text(text = addressModel.address)
            Row {
                Text(text = addressModel.city + ", ")
                Text(text = addressModel.codPostal)
            }
        }
        IconButton(onClick = { onClickEdit() }) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
        }
    }
    Spacer(modifier = Modifier.size(16.dp))
    Divider()
    Spacer(modifier = Modifier.size(16.dp))
}

@Composable
fun AddDialogContent(
    name: String,
    address: String,
    city: String,
    codPostal: String,
    isSaveEnable: Boolean,
    onClickClose: () -> Unit,
    onClickSave: () -> Unit,
    addressViewModel: AddressViewModel,
    update: Boolean,
    onClickUpdate: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                IconButton(onClick = { onClickClose() }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "close")
                }
            }
            Text(
                text = "Añadir dirección",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            OutlinedTextField(
                modifier = Modifier.padding(bottom = 8.dp),
                value = name,
                onValueChange = {
                    addressViewModel.onTextFieldsChanged(
                        it,
                        address,
                        city,
                        codPostal
                    )
                },
                label = { Text(text = "Nombre") },
                maxLines = 1,
                placeholder = {
                    Text(
                        text = "Como llamar a esta dirección"
                    )
                })
            OutlinedTextField(
                modifier = Modifier.padding(bottom = 8.dp),
                value = address,
                onValueChange = { addressViewModel.onTextFieldsChanged(name, it, city, codPostal) },
                label = { Text(text = "Dirección") },
                placeholder = {
                    Text(
                        text = "Introduce tu dirección completa"
                    )
                })
            OutlinedTextField(
                modifier = Modifier.padding(bottom = 8.dp),
                value = city,
                onValueChange = {
                    addressViewModel.onTextFieldsChanged(
                        name,
                        address,
                        it,
                        codPostal
                    )
                },
                label = { Text(text = "Localidad") },
            )
            OutlinedTextField(
                modifier = Modifier.padding(bottom = 8.dp),
                value = codPostal,
                onValueChange = { addressViewModel.onTextFieldsChanged(name, address, city, it) },
                label = { Text(text = "Código postal") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            if (update) {
                Button(
                    onClick = { onClickUpdate() },
                    enabled = isSaveEnable,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Filled.Update, contentDescription = "update")
                        Text(text = "Actualizar", Modifier.padding(horizontal = 8.dp))
                    }
                }
            } else {
                Button(
                    onClick = { onClickSave() },
                    enabled = isSaveEnable,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Filled.Save, contentDescription = "save")
                        Text(text = "Guardar", Modifier.padding(horizontal = 8.dp))
                    }
                }
            }
        }
    }
}
