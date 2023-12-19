package es.fjmarlop.pizzettApp.vistas.gestion.mainGestion.ui

import android.os.Handler
import android.os.Looper
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.navigation.Navegadores
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.dataBase.Remote.models.CategoriaModel
import es.fjmarlop.pizzettApp.dataBase.Remote.models.EmpleadoModel
import es.fjmarlop.pizzettApp.dataBase.Remote.models.IngredientsModel
import es.fjmarlop.pizzettApp.dataBase.Remote.models.PedidoModel
import es.fjmarlop.pizzettApp.dataBase.Remote.models.ProductoModel
import es.fjmarlop.pizzettApp.dataBase.Remote.models.TamaniosModel
import es.fjmarlop.pizzettApp.vistas.gestion.Productos.domain.AddProductoUseCase
import es.fjmarlop.pizzettApp.vistas.gestion.Productos.domain.DeleteProductoUseCase
import es.fjmarlop.pizzettApp.vistas.gestion.Productos.domain.GetIngredientesUseCase
import es.fjmarlop.pizzettApp.vistas.gestion.Productos.domain.GetProductosUseCase
import es.fjmarlop.pizzettApp.vistas.gestion.empleados.domain.DeleteEmpleadoUseCase
import es.fjmarlop.pizzettApp.vistas.gestion.empleados.domain.GetEmpleadosUseCase
import es.fjmarlop.pizzettApp.vistas.gestion.empleados.domain.InsertEmpleadoUseCase
import es.fjmarlop.pizzettApp.vistas.gestion.pedidos.domain.GetAllPedidosUseCase
import es.fjmarlop.pizzettApp.vistas.gestion.pedidos.domain.UpdateEstadoUseCase
import es.fjmarlop.pizzettApp.vistas.login.domain.googleLogin.GoogleAuthUiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
    private val addProducto: AddProductoUseCase,
    private val borrarProducto: DeleteProductoUseCase
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

    private val _listaIngredientes = MutableStateFlow<List<IngredientsModel>>(emptyList())
    val listaIngredientes: StateFlow<List<IngredientsModel>> = _listaIngredientes

    private val _listaIngToSave = MutableStateFlow<List<String>>(emptyList())

    private val _listaTamanosToSave = MutableStateFlow<List<TamaniosModel>>(emptyList())

    private val _nombreProducto = MutableStateFlow("")
    val nombreProducto: StateFlow<String> = _nombreProducto

    private val _urlImagenProducto = MutableStateFlow("")
    val urlImagenProducto: StateFlow<String> = _urlImagenProducto

    private val _descripcionProducto = MutableStateFlow("")
    val descripcionProducto: StateFlow<String> = _descripcionProducto

    private val _pvp = MutableStateFlow("0.0")
    val pvp: StateFlow<String> = _pvp

    private val _categoria = MutableStateFlow("")
    val categoria: StateFlow<String> = _categoria

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    /**
     *  Método para iniciar la actualización periódica de pedidos.
     */
    fun startUpdating() {
        runnable = object : Runnable {
            override fun run() {
                getAllPedidos()
                // Programar la próxima ejecución después de x segundos
                handler.postDelayed(this, 10000)
            }
        }
        // Iniciar la actualización periódica
        handler.postDelayed(runnable, 0)
    }

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
        startUpdating()
    }

    override fun onCleared() {
        super.onCleared()
        stopUpdating()
    }

    fun stopUpdating() {
        // Detener la actualización periódica
        handler.removeCallbacks(runnable)
    }

    private fun getAllIngredientes() {
        viewModelScope.launch(Dispatchers.IO) {
            _listaIngredientes.value = getIngredientes()
        }
    }

    fun getAllEmpleados() {
        viewModelScope.launch(Dispatchers.IO) {
            _listaEmpleados.value = getEmpleados()
        }
    }

    fun getAllProductos() {
        viewModelScope.launch(Dispatchers.IO) {
            _listaProductos.value = getProductos()
        }
    }

    private fun getAllPedidos() {
        viewModelScope.launch(Dispatchers.IO) {
            _listaPedidos.value = getPedidos()
            _listaPedidos.value = _listaPedidos.value.sortedByDescending { it.id }
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

    fun eliminarProducto(it: Int, navHostController: NavHostController) {
        viewModelScope.launch(Dispatchers.IO) {
            borrarProducto(it)
            delay(1500)
            getAllProductos()
        }

        navHostController.popBackStack()
        navegadores.navigateToGestion(navHostController)
    }

    fun onNombreProductoChange(nombre: String) {
        _nombreProducto.value = nombre
    }

    fun onDescripcionProductoChange(descripcion: String) {
        _descripcionProducto.value = descripcion
    }

    fun onUrlImagenProductoChange(urlImagen: String) {
        _urlImagenProducto.value = urlImagen
    }

    fun saveIngredientes(list: List<String>) {
        _listaIngToSave.value = list
    }

    fun saveTamano(it: TamaniosModel) {
        _listaTamanosToSave.value = _listaTamanosToSave.value + it
        _pvp.value = 0.0.toString()
    }

    /**
     *  Función para actualizar el estado del pedido en el "botón rápido"
     *  @param pedido pedido a actualizar
     * */
    fun actualizarEstado(pedido: PedidoModel) {
        if (pedido.estado == "Sin confirmar") {
            viewModelScope.launch(Dispatchers.IO) {
                updateEstado(pedido.id, "Confirmado")
                getAllPedidos()
            }
        }
        if (pedido.estado == "Confirmado") {
            viewModelScope.launch(Dispatchers.IO) {
                updateEstado(pedido.id, "Entregado")
                getAllPedidos()
            }
        }
    }

    fun onPvpChange(pvp: String) {
        _pvp.value = pvp
    }

    fun saveCategoria(cat: String) {
        _categoria.value = cat
    }

    fun saveProducto(
        nombreProducto: String,
        descripcionProducto: String,
        urlImagenProducto: String
    ) {
        var listIng: List<IngredientsModel> = emptyList()

        listaIngredientes.value.forEach {
            if (_listaIngToSave.value.contains(it.ingredientName)) {
                val ing = IngredientsModel(
                    ingredientName = it.ingredientName,
                    id = it.id
                )
                listIng = listIng + ing
            }
        }

        val producto = ProductoModel(
            id_producto = null,
            nombre_producto = nombreProducto,
            descripcion = descripcionProducto,
            imagen_producto = urlImagenProducto,
            categoria = _categoria.value.map {
                CategoriaModel(
                    nombre_categoria = _categoria.value,
                    id_categoria = null
                )
            }.toSet(),
            ingredients = listIng.toSet(),
            tamanios = _listaTamanosToSave.value.toSet()
        )
        viewModelScope.launch(Dispatchers.IO) {
            addProducto(producto)
        }
    }


}