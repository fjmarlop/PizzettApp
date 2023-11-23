package es.fjmarlop.pizzettApp.vistas.cliente.compra.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.navigation.Navegadores
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.dataBase.Remote.models.LineaPedidoModel
import es.fjmarlop.pizzettApp.dataBase.Remote.models.PedidoModel
import es.fjmarlop.pizzettApp.dataBase.local.models.AddressModel
import es.fjmarlop.pizzettApp.dataBase.local.models.UserModel
import es.fjmarlop.pizzettApp.vistas.cliente.compra.domain.CompraDomainService
import es.fjmarlop.pizzettApp.vistas.cliente.detailsAccount.domain.DetailsProfileDomainService
import es.fjmarlop.pizzettApp.vistas.cliente.main.ui.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class CompraViewModel @Inject constructor(
    private val utils: Utils,
    private val navegadores: Navegadores,
    private val domainService: DetailsProfileDomainService,
    private val compraDomainService: CompraDomainService
) : ViewModel() {


    private val _local = MutableStateFlow(false)
    val local: StateFlow<Boolean> = _local

    private val _domicilio = MutableStateFlow(true)
    val domicilio: StateFlow<Boolean> = _domicilio

    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel> = _user

    private val _listAddress = MutableStateFlow<List<AddressModel>?>(null)
    val listAddress: StateFlow<List<AddressModel>?> = _listAddress

    private val _direccionEnTexto = MutableStateFlow("")
    val direccionEnTexto: StateFlow<String> = _direccionEnTexto

    private val _showPedido = MutableStateFlow(true)
    val showPedido: StateFlow<Boolean> = _showPedido

    private val _tramitarCompra = MutableStateFlow(false)
    val tramitarCompra: StateFlow<Boolean> = _tramitarCompra

    private val _showHorno = MutableStateFlow(false)
    val showHorno: StateFlow<Boolean> = _showHorno

    var fechaPedido = ""
    var totalPedido = 0.0
    private var indexDireccion = 0
    fun goToDetalles(navHostController: NavHostController) {
        navegadores.navigateToDetailsProfile(navHostController)
    }

    fun onChangleLocal() {
        _local.value = false
        _domicilio.value = true
        viewModelScope.launch(Dispatchers.IO) {
            _listAddress.value = compraDomainService.getListAddres(_user.value?.email.toString())
        }

    }

    fun onChangeDomicilio() {
        _domicilio.value = false
        _local.value = true
    }

    fun getUser() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _user.postValue(domainService.getUser())

            }
        }
    }

    fun getListAddress(email: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _listAddress.value = (compraDomainService.getListAddres(email))
            }
        }
    }

    fun goTolistAddress(navHostController: NavHostController) {
        navegadores.navigateToAddress(navHostController)
    }


    private fun validarFinalizar(nombre: String?, email: String?, total: Double, fecha: String): Boolean {
        return (nombre!!.isNotEmpty() && email!!.isNotEmpty() && total > 0.0 && fecha.isNotEmpty())
    }

    fun finalizar(listaPedido: List<LineaPedidoModel>, mainViewModel: MainViewModel, navHostController: NavHostController) {

        val tipoDeEntrega = if (!_domicilio.value) "Domicilio" else "Local"

        var direccionEnvio = ""

        val totalDireciones = _listAddress.value?.size

        if (totalDireciones == 1 && tipoDeEntrega == "Domicilio") {
            val direccion = _listAddress.value?.get(0)
            direccionEnvio =
                direccion?.address + ", " + direccion?.codPostal + ", " + direccion?.city
        }
        if (totalDireciones != null && totalDireciones > 1 && tipoDeEntrega == "Domicilio") {
            val direccion = _listAddress.value?.get(indexDireccion - 1)
            direccionEnvio =
                direccion?.address + ", " + direccion?.codPostal + ", " + direccion?.city
        }
        val fechaCompromiso = if(fechaPedido == "Elige una fecha") "" else fechaPedido

        val nombre = _user.value?.name
        val email = _user.value?.email
        val phone = if (_user.value?.phone == null) "" else _user.value?.phone

        val pedido = PedidoModel(
            nombreCliente = nombre!!,
            emailCliente = email!!,
            telefonoCliente = phone!!,
            direccionEnvio = direccionEnvio,
            fechaCreacion = Calendar.getInstance().time.toString(),
            fechaPedido = fechaPedido,
            total = totalPedido,
            estado = "En proceso",
            tipoEntrega = tipoDeEntrega,
            lineasPedido = listaPedido
        )

        if (validarFinalizar(nombre, email, totalPedido, fechaCompromiso)) {
            viewModelScope.launch(Dispatchers.IO)
            {
                val response = compraDomainService.finalizarPedido(pedido)
                if (response > 0) withContext(Dispatchers.Main) {
                    utils.mensajeToast("Tu pedido se ha enviado con exito")
                    /* LIMPIAR LOS CAMPOS Y PASAR A LA PANTALLA DE PEDIDOS */
                    mainViewModel._listaLineasPedido.value = emptyList()
                    mainViewModel.lineasPedido.clear()
                    mainViewModel._lineasTotal.value = 0
                    _tramitarCompra.value = false
                    _showHorno.value = true
                }
                else withContext(Dispatchers.Main) { utils.mensajeToast("Ha habido un error al enviar tu pedido") }
            }
        } else {
            utils.mensajeToast("Revisa todos los campos")
        }
    }

    fun direccionSelected(it: Int) {
        indexDireccion = it
        val dir = _listAddress.value?.get(it - 1)
        _direccionEnTexto.value = dir?.address + ", " + dir?.codPostal + ", " + dir?.city
    }

    fun goToHistory(navHostController: NavHostController) {
       viewModelScope.launch(Dispatchers.Main){
           navegadores.navigateToHistorico(navHostController)
       }
    }

    fun mostrarTramitar() {
        _showPedido.value = false
        _tramitarCompra.value = true
    }
}








