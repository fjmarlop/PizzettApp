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
import es.fjmarlop.pizzettApp.vistas.cliente.compra.domain.FinalizarPedidoUseCase
import es.fjmarlop.pizzettApp.vistas.cliente.compra.domain.GetListAddressUseCase
import es.fjmarlop.pizzettApp.vistas.cliente.detailsAccount.domain.DetailsProfileDomainService
import es.fjmarlop.pizzettApp.vistas.cliente.main.ui.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

/**
 * ViewModel para la pantalla de compra, que gestiona la finalización de pedidos, la selección de direcciones y otras operaciones relacionadas con la compra.
 *
 * @param utils Utilidades para funciones comunes.
 * @param navegadores Clase que maneja la navegación entre pantallas.
 * @param domainService Servicio de dominio para operaciones relacionadas con el perfil del usuario.
 * @param getListAddres UseCase para obtener una lista de direcciones.
 * @param finalizarPedido UseCase para finalizar un pedido.
 */
@HiltViewModel
class CompraViewModel @Inject constructor(
    private val utils: Utils,
    private val navegadores: Navegadores,
    private val domainService: DetailsProfileDomainService,
    private val finalizarPedido: FinalizarPedidoUseCase,
    private val getListAddres: GetListAddressUseCase
) : ViewModel() {


    val _local = MutableStateFlow(false)
    val local: StateFlow<Boolean> = _local

    val _domicilio = MutableStateFlow(true)
    val domicilio: StateFlow<Boolean> = _domicilio

    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel> = _user

    val _listAddress = MutableStateFlow<List<AddressModel>?>(null)
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

    //probar antes de subir, posible solución a las fallas de carga de direcciones multiples
    init {
        getListAddress(_user.value?.email.toString())
    }

    /**
     * Navega hacia la pantalla de detalles del perfil.
     */
    fun goToDetalles(navHostController: NavHostController) {
        navegadores.navigateToDetailsProfile(navHostController)
    }

    /**
     * Cambia el modo de entrega a local y actualiza la lista de direcciones disponibles.
     */
    fun onChangleLocal() {
        _local.value = false
        _domicilio.value = true
        viewModelScope.launch(Dispatchers.IO) {
            _listAddress.value = getListAddres(_user.value?.email.toString())
        }

    }

    /**
     * Cambia el modo de entrega a domicilio.
     */
    fun onChangeDomicilio() {
        _domicilio.value = false
        _local.value = true
    }

    /**
     * Obtiene la información del usuario.
     */
    fun getUser() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _user.postValue(domainService.getUser())

            }
        }
    }

    /**
     * Obtiene la lista de direcciones asociadas al usuario.
     *
     * @param email Correo electrónico del usuario.
     */
    fun getListAddress(email: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _listAddress.value = (getListAddres(email))
            }
        }
    }

    /**
     * Navega hacia la pantalla de lista de direcciones.
     */
    fun goTolistAddress(navHostController: NavHostController) {
        navegadores.navigateToAddress(navHostController)
    }


    /**
     * Valida que todos los campos obligatorios hayan sido rellenados.
     * @param nombre Nombre del usuario.
     * @param email Correo electrónico del usuario.
     * @param total Total del pedido.
     * @param fecha Fecha del pedido.
     * @return True si todos los campos son correctos, False en caso contrario.
     */

    private fun validarFinalizar(
        nombre: String?,
        email: String?,
        total: Double,
        fecha: String
    ): Boolean {
        return (nombre!!.isNotEmpty() && email!!.isNotEmpty() && total > 0.0 && fecha.isNotEmpty())
    }

    /**
     * Inicia el proceso de finalización de un pedido.
     *
     * @param listaPedido Lista de modelos de líneas de pedido.
     * @param mainViewModel ViewModel principal que gestiona la información del carrito de compras.
     */
    fun finalizar(
        listaPedido: List<LineaPedidoModel>,
        mainViewModel: MainViewModel
    ) {

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
        val fechaCompromiso = if (fechaPedido == "Elige una fecha") "" else fechaPedido

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
            estado = "Sin confirmar",
            tipoEntrega = tipoDeEntrega,
            lineasPedido = listaPedido,
            id = 0
        )

        if (validarFinalizar(nombre, email, totalPedido, fechaCompromiso)) {
            viewModelScope.launch(Dispatchers.IO)
            {
                val response = finalizarPedido(pedido)
                if (response > 0) withContext(Dispatchers.Main) {
                    utils.mensajeToast("Tu pedido se ha enviado con exito")
                    /* LIMPIAR LOS CAMPOS Y PASAR A LA PANTALLA DE PEDIDOS */
                    _showHorno.value = true
                    resetearCampos(mainViewModel)
                }
                else withContext(Dispatchers.Main) { utils.mensajeToast("Ha habido un error al enviar tu pedido") }
            }
        } else {
            utils.mensajeToast("Revisa todos los campos")
        }
    }

    /**
     * Muestra la dirección seleccionada por el usuario
     * cuando existen almacenadas mas de una
     */
    fun direccionSelected(it: Int) {
        indexDireccion = it
        val dir = _listAddress.value?.get(it - 1)
        _direccionEnTexto.value = dir?.address + ", " + dir?.codPostal + ", " + dir?.city
    }

    /**
     * Navega hacia la pantalla de historial de pedidos.
     * @param navHostController Controlador de navegación de Jetpack Compose.
     * */
    fun goToHistory(navHostController: NavHostController) {
        viewModelScope.launch(Dispatchers.Main) {
            navegadores.navigateToHistorico(navHostController)
            _showHorno.value = false
        }
    }

    /**
     * Activa el botón de tramitar compra.
     * */
    fun mostrarTramitar() {
        _showPedido.value = false
        _tramitarCompra.value = true
    }

    /**
     * Restea los campos de la compra.
     */
    private fun resetearCampos(mainViewModel: MainViewModel) {
        mainViewModel._listaLineasPedido.value = emptyList()
        mainViewModel.lineasPedido.clear()
        mainViewModel._lineasTotal.value = 0
        _tramitarCompra.value = false
        _showPedido.value = true
    }

    fun mostrarPedido() {
        viewModelScope.launch {
            _showPedido.value = true;
            _tramitarCompra.value = false;
        }
    }

}








