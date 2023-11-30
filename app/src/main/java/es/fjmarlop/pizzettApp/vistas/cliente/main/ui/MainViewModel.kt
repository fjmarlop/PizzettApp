package es.fjmarlop.pizzettApp.vistas.cliente.main.ui

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.navigation.Navegadores
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.dataBase.Remote.models.LineaPedidoModel
import es.fjmarlop.pizzettApp.dataBase.Remote.models.ProductoLineaPedidoModel
import es.fjmarlop.pizzettApp.dataBase.Remote.models.ProductoModel
import es.fjmarlop.pizzettApp.dataBase.Remote.models.TamaniosModel
import es.fjmarlop.pizzettApp.dataBase.local.models.UserModel
import es.fjmarlop.pizzettApp.vistas.cliente.main.domain.MainDomainService
import es.fjmarlop.pizzettApp.vistas.cliente.main.domain.ProductoDomainService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * ViewModel principal para la gestión de la lógica de la interfaz de usuario.
 *
 * Este ViewModel utiliza servicios del dominio [MainDomainService] y [ProductoDomainService] para gestionar
 * la lógica relacionada con los usuarios y productos en la interfaz de usuario.
 *
 * @property utils Instancia de [Utils] utilizada para funciones de utilidad.
 * @property navegadores Instancia de [Navegadores] utilizada para la navegación en la aplicación.
 * @property mainDomainService Instancia de [MainDomainService] utilizado para acceder a la lógica principal del usuario.
 * @property productoDomainService Instancia de [ProductoDomainService] utilizado para acceder a la lógica del dominio de productos.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val utils: Utils,
    private val navegadores: Navegadores,
    private val mainDomainService: MainDomainService,
    private val productoDomainService: ProductoDomainService
) : ViewModel() {


    private val _index = MutableStateFlow(0)
    val index: StateFlow<Int> = _index.asStateFlow()

    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel> = _user

    //activa  el botón de añadir al pedido si cumple con los requisitos
    //(tamaño seleccionado y cantidad > 0)
    private val _activateButtonAddLine = MutableStateFlow(false)
    val activateButtonAddLine: StateFlow<Boolean> = _activateButtonAddLine

    //valor que recoge el número de unidades elegidas por el usuario
    var _cantidad = MutableStateFlow(0)
    val cantidad: StateFlow<Int> = _cantidad

    //estado para manejar el total de lineas introducidas para que aparezcan en el
    //Badge del carrito
    val _lineasTotal = MutableStateFlow(0)
    val lineasTotal: StateFlow<Int> = _lineasTotal

    // variable que recoge todas las línea de pedido que introduzca el usuario
    var lineasPedido: MutableList<LineaPedidoModel> =
        emptyList<LineaPedidoModel>().toMutableList()

    // variable que recoge el tamaño seleccionado por el cliente,
    // sobretodo tener en cuenta las pizzas que existen 3 tamaños
    // este set se agrega al producto seleccionado
    private var tamanoSeleccionado: MutableSet<TamaniosModel> =
        emptySet<TamaniosModel>().toMutableSet()

    //valor del tamaño y pvp del producto seleccionado
    private lateinit var tamanoSelected: TamaniosModel

    //comprobar si el tamaño ha sido seleccionado
    private var isTamanoSelected = false

    //estado para comprobar las líneas totales del pedido del usuario.
    var _listaLineasPedido = MutableStateFlow<List<LineaPedidoModel>>(emptyList())
    val listaLineasPedido: StateFlow<List<LineaPedidoModel>> = _listaLineasPedido

    /**
     * Obtiene los productos recomendados al iniciar el viewModel.
     */
    init {
        viewModelScope.launch(Dispatchers.IO) {
            getProductosParaRecomendados()
        }
    }

    /**
     * función para navegar entre las vistas del buttomBar navigation
     * @param int: Int para indicar la vista que queremos mostrar
     * @param navHostController: NavHostController para navegar entre las vistas
     */
    fun onClickInicio(int: Int, navHostController: NavHostController) {
        viewModelScope.launch {
            _index.emit(int)
        }
        navegadores.navigateToMain(navHostController)
    }

    /**
     * función para navegar entre las vistas del buttomBar navigation
     * @param int: Int para indicar la vista que queremos mostrar
     * @param navHostController: NavHostController para navegar entre las vistas
     */
    fun onClickOfertas(int: Int, navHostController: NavHostController) {
        viewModelScope.launch {
            _index.emit(int)
        }
        navegadores.navigateToOfertas(navHostController)
    }

    /**
     * función para navegar entre las vistas del buttomBar navigation
     * @param int: Int para indicar la vista que queremos mostrar
     * @param navHostController: NavHostController para navegar entre las vistas
     */
    fun onClickCarrito(int: Int, navHostController: NavHostController) {
        viewModelScope.launch {
            _index.emit(int)
        }
        navegadores.navigateToCompra(navHostController)
    }

    /**
     * función para navegar entre las vistas del buttomBar navigation
     * @param int: Int para indicar la vista que queremos mostrar
     * @param navHostController: NavHostController para navegar entre las vistas
     */
    fun onClickPedidos(int: Int, navHostController: NavHostController) {
        viewModelScope.launch {
            _index.emit(int)
        }
        navegadores.navigateToHistorico(navHostController)
    }

    /**
     * función para navegar entre las vistas del buttomBar navigation
     * @param int: Int para indicar la vista que queremos mostrar
     * @param navHostController: NavHostController para navegar entre las vistas
     */
    fun onClickCuenta(int: Int, navHostController: NavHostController) {
        viewModelScope.launch {
            _index.emit(int)
        }
        _showRecomendados.value = true
        navegadores.navigateToProfile(navHostController)
    }

    /**
     * Obtiene el nombre del usuario, si no exite el nombre de usuario
     * usará el correo que se ha usado para el inicio de sesión.
     * @param email: Correo del usuario
     * @return String con el nombre
     */
    fun obtenerUsername(email: String): String {
        val emailPattern = Patterns.EMAIL_ADDRESS.matcher(email)
        return if (emailPattern.matches()) {
            val partes = email.split("@")
            partes.firstOrNull() ?: ""
        } else {
            val partes = email.split(" ")
            partes.firstOrNull() ?: ""
        }
    }

    /**
     * Resetea valores
     */
    fun resetIndex() {
        viewModelScope.launch {
            _index.value = 0
            _categoria.value = "Hoy te recomendamos..."
            _showRecomendados.value = true
        }
    }

    /**
     * Guarda el usuario en la base de datos (se lanza cuando el usuario inicia sesión).
     */
    fun addUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = Firebase.auth.currentUser
            user?.let {
                val email = it.email
                val name = it.displayName
                val userModel = UserModel(email ?: "", name ?: "", "")
                if (mainDomainService.getUserCount() <= 0) {
                    mainDomainService.addUser(userModel)
                    Log.i("Info PizzettApp", "Se ha introducido el usuario en room")
                }
            }
        }
    }

    /**
     * Obtiene el usuario de la base de datos.
     */
    fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            if (mainDomainService.getUserCount() > 0) {
                val user = mainDomainService.getUser()
                _user.postValue(user)
            }
        }
    }

    fun onTamanoSelected(item: TamaniosModel) {
        tamanoSelected = item
        isTamanoSelected = true
    }

    fun restarCantidad() {
        _cantidad.value -= 1
        _activateButtonAddLine.value = comprobarCantidad()
    }

    fun aumentarCantidad() {
        _cantidad.value += 1
        _activateButtonAddLine.value = comprobarCantidad()
    }

    private fun comprobarCantidad(): Boolean {
        return _cantidad.value > 0
    }

    fun resetValues() {
        _cantidad.value = 0
        isTamanoSelected = false
        _activateButtonAddLine.value = comprobarCantidad()
    }

    /**
     * Añade Linea de pedido a la lista de lineas de pedido
     */
    fun addOrderLine(producto: ProductoModel) {

        tamanoSeleccionado.add(tamanoSelected)

        val productoElegido = ProductoLineaPedidoModel(
            idProducto = producto.id_producto,
            nombre_producto = producto.nombre_producto,
            categoria = producto.categoria.joinToString { it.nombre_categoria },
            tamano = tamanoSelected.tamano,
            pvp = tamanoSelected.pvp
        )

        val linea = LineaPedidoModel(lineasPedido.size + 1, productoElegido, _cantidad.value)

        lineasPedido.add(linea)
        _lineasTotal.value = lineasPedido.size

        //para controla el estado para engancharlo a la UI
        _listaLineasPedido.value = lineasPedido
    }

    private val _categoria = MutableStateFlow("Hoy te recomendamos...")
    val categoria: StateFlow<String> = _categoria


    private var _productsList = MutableStateFlow<List<ProductoModel>>(emptyList())
    val productsList: StateFlow<List<ProductoModel>> = _productsList

    private var _productsListForRandom = MutableStateFlow<List<ProductoModel>>(emptyList())
    val productsListForRandom: StateFlow<List<ProductoModel>> = _productsListForRandom

    private val _showRecomendados = MutableStateFlow(true)
    val showRecomendados: StateFlow<Boolean> = _showRecomendados


    private fun getProductosParaRecomendados() {

        viewModelScope.launch {
            val list: List<ProductoModel>
            val listRandom = mutableSetOf<ProductoModel>()
            withContext(Dispatchers.IO) {
                list = productoDomainService.getProductosParaRecomendados()
            }
            if (list.isNotEmpty()) {
                for (i in 1..4) {
                    listRandom.add(list.random())
                }
                _productsListForRandom.value = listRandom.toList()
            } else {
                utils.mensajeToast("No podemos mostrar los datos, inténtalo mas tarde.")
            }
        }
    }


    private fun getProductosPorCategoria(cat: String) {
        viewModelScope.launch() {
            val list = productoDomainService.getProductosPorCategoria(cat)
            if (list.isEmpty()) {
                utils.mensajeToast("No podemos mostrar los datos, inténtalo mas tarde.")
            } else {
                _productsList.value = list
            }
        }
    }

    fun onClickCategoria(categoria: String) {
        getProductosPorCategoria(categoria)
        _categoria.value = categoria
        _showRecomendados.value = false
    }

    fun deleteLinea(idLineaPedidoModel: Int) {
        val linea = lineasPedido.find { it.idLineaPedidoModel == idLineaPedidoModel }
        if (linea != null) {
            lineasPedido.remove(linea)
        }
        _lineasTotal.value = lineasPedido.size
    }
}
