package es.fjmarlop.pizzettApp.vistas.gestion.mainGestion.ui

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.navigation.Navegadores
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.dataBase.Remote.models.EmpleadoModel
import es.fjmarlop.pizzettApp.dataBase.Remote.models.PedidoModel
import es.fjmarlop.pizzettApp.dataBase.Remote.models.ProductoModel
import es.fjmarlop.pizzettApp.dataBase.Remote.models.TamaniosModel
import es.fjmarlop.pizzettApp.vistas.gestion.Productos.domain.GetIngredientesUseCase
import es.fjmarlop.pizzettApp.vistas.gestion.Productos.domain.GetProductosUseCase
import es.fjmarlop.pizzettApp.vistas.gestion.empleados.domain.DeleteEmpleadoUseCase
import es.fjmarlop.pizzettApp.vistas.gestion.empleados.domain.GetEmpleadosUseCase
import es.fjmarlop.pizzettApp.vistas.gestion.empleados.domain.InsertEmpleadoUseCase
import es.fjmarlop.pizzettApp.vistas.gestion.pedidos.domain.GetAllPedidosUseCase
import es.fjmarlop.pizzettApp.vistas.gestion.pedidos.domain.UpdateEstadoUseCase
import es.fjmarlop.pizzettApp.vistas.login.domain.googleLogin.GoogleAuthUiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainGestionViewModel @Inject constructor(
    private val navegadores: Navegadores,
    private val utils: Utils,
    private val getProductos: GetProductosUseCase,
    private val getPedidos: GetAllPedidosUseCase,
    private val getEmpleados: GetEmpleadosUseCase,
    private val insertEmpleado: InsertEmpleadoUseCase,
    private val deleteEmpleado: DeleteEmpleadoUseCase,
    private val getIngredientes: GetIngredientesUseCase,
    private val updateEstado: UpdateEstadoUseCase,
) : ViewModel() {

    private val _nombreEmpleado = MutableStateFlow("")
    val nombreEmpleado: StateFlow<String> = _nombreEmpleado

    private val _emailEmpleado = MutableStateFlow("")
    val emailEmpleado: StateFlow<String> = _emailEmpleado

    private val _validarEmail = MutableStateFlow(false)
    val validarEmail: StateFlow<Boolean> = _validarEmail

    private val _listaProductos = MutableStateFlow<List<ProductoModel>>(emptyList())
    val listaProductos: StateFlow<List<ProductoModel>> = _listaProductos

    private val _listaPedidos = MutableStateFlow<List<PedidoModel>>(emptyList())
    val listaPedidos: StateFlow<List<PedidoModel>> = _listaPedidos

    private val _listaEmpleados = MutableStateFlow<List<EmpleadoModel>>(emptyList())
    val listaEmpleados: StateFlow<List<EmpleadoModel>> = _listaEmpleados

    private val _listaIngredientes = MutableStateFlow<List<String>>(emptyList())
    val listaIngredientes: StateFlow<List<String>> = _listaIngredientes

    private val _listaIngToSave = MutableStateFlow<List<String>>(emptyList())

    private val _listaTamanosToSave = MutableStateFlow<List<TamaniosModel>>(emptyList())

    fun onTextEmpleadoChange(nombreEmpleado: String, emailEmpleado: String) {
        _nombreEmpleado.value = nombreEmpleado
        _emailEmpleado.value = emailEmpleado
        _validarEmail.value = Patterns.EMAIL_ADDRESS.matcher(emailEmpleado).matches()
    }

    init {
        getAllProductos()
        getAllPedidos()
        getAllEmpleados()
        getAllIngredientes()

    }

    private fun getAllIngredientes() {
        viewModelScope.launch(Dispatchers.IO) {
            _listaIngredientes.value = getIngredientes()
        }
    }

    private fun getAllEmpleados() {
        viewModelScope.launch(Dispatchers.IO) {
            _listaEmpleados.value = getEmpleados()
        }
    }

    private fun getAllProductos() {
        viewModelScope.launch(Dispatchers.IO) {
            _listaProductos.value = getProductos()
        }
    }

    private fun getAllPedidos() {
        viewModelScope.launch(Dispatchers.IO) {
            _listaPedidos.value = getPedidos()
        }
    }

    fun cerrarSesion(googleAuthUiClient: GoogleAuthUiClient, navHostController: NavHostController) {
        viewModelScope.launch {
            googleAuthUiClient.signOut()
            navHostController.popBackStack()
            navegadores.navigateToLogin(navHostController)
        }
    }

    fun agregarEmpleado(emailEmpleado: String, nombreEmpleado: String) {
        val empleado = EmpleadoModel(
            empleadoEmail = emailEmpleado,
            empleadoName = nombreEmpleado,
            id = null
        )
        viewModelScope.launch(Dispatchers.IO) {
            insertEmpleado(empleado)
            _listaEmpleados.value = getEmpleados()
        }

    }

    fun eliminarEmpleado(it: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteEmpleado(it)
            _listaEmpleados.value = getEmpleados()
        }
    }

    fun eliminarProducto(it: Int) {

    }

    fun onNombreProductoChange(nombre: String) {

    }

    fun onDescripcionProductoChange(descripcion: String) {

    }

    fun onUrlImagenProductoChange(urlImagen: String) {

    }

    fun agregarIngrediente(ing: String) {

    }

    fun saveIngredientes(list: List<String>) {
        _listaIngToSave.value = list
    }

    fun saveTamano(it: TamaniosModel) {
        _listaTamanosToSave.value = _listaTamanosToSave.value + it
    }

    fun actualizarEstado(pedido: PedidoModel) {
        if (pedido.estado == "En proceso") {
            viewModelScope.launch(Dispatchers.IO) {
                pedido.id?.let { updateEstado(it, "Confirmado") }
                getAllPedidos()
            }
        }
        if (pedido.estado == "Confirmado") {
            viewModelScope.launch(Dispatchers.IO) {
                pedido.id?.let { updateEstado(it, "Entregado") }
                getAllPedidos()
            }
        }
    }
}
