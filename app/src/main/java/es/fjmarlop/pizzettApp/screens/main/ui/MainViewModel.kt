package es.fjmarlop.pizzettApp.screens.main.ui

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
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.core.retrofit.models.LineaPedidoModel
import es.fjmarlop.pizzettApp.core.retrofit.models.ProductoLineaPedidoModel
import es.fjmarlop.pizzettApp.core.retrofit.models.ProductoModel
import es.fjmarlop.pizzettApp.core.retrofit.models.TamaniosModel
import es.fjmarlop.pizzettApp.core.roomDB.models.UserModel
import es.fjmarlop.pizzettApp.screens.main.domain.MainDomainService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val utils: Utils,
    private val mainDomainService: MainDomainService
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
    private val _cantidad = MutableStateFlow(0)
    val cantidad: StateFlow<Int> = _cantidad

    //estado para manejar el total de lineas introducidas para que aparezcan en el
    //Badge del carrito
    private val _lineasTotal = MutableStateFlow(0)
    val lineasTotal: StateFlow<Int> = _lineasTotal

    // variable que recoge todas las línea de pedido que introduzca el usuario
    private var lineasPedido: MutableList<LineaPedidoModel> = emptyList<LineaPedidoModel>().toMutableList()

    // variable que recoge el tamaño seleccionado por el cliente,
    // sobretodo tener en cuenta las pizzas que existen 3 tamaños
    // este set se agrega al producto seleccionado
    private var tamanoSeleccionado: MutableSet<TamaniosModel> = emptySet<TamaniosModel>().toMutableSet()

    //valor del tamaño y pvp del producto seleccionado
    private lateinit var tamanoSelected: TamaniosModel

    //comprobar si el tamaño ha sido seleccionado
    private var isTamanoSelected = false

    //estado para comprobar las líneas totales del pedido del usuario.
    private var _listaLineasPedido = MutableStateFlow<List<LineaPedidoModel>>(emptyList())
    val listaLineasPedido: StateFlow<List<LineaPedidoModel>> = _listaLineasPedido


    fun onClickInicio(int: Int, navHostController: NavHostController) {
        viewModelScope.launch {
            _index.emit(int)
        }
        utils.navigateToMain(navHostController)
    }

    fun onClickOfertas(int: Int, navHostController: NavHostController) {
        viewModelScope.launch {
            _index.emit(int)
        }
        utils.navigateToOfertas(navHostController)
    }

    fun onClickCarrito(int: Int, navHostController: NavHostController) {
        viewModelScope.launch {
            _index.emit(int)
        }
         utils.navigateToCompra(navHostController)
    }

    fun onClickPedidos(int: Int, navHostController: NavHostController) {
        viewModelScope.launch {
            _index.emit(int)
        }
        //utils.navigateTo(navHostController, Rutas.MainScreen)
    }

    fun onClickCuenta(int: Int, navHostController: NavHostController) {
        viewModelScope.launch {
            _index.emit(int)
        }
        utils.navigateToProfile(navHostController)
    }

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

    fun resetIndex() {
        viewModelScope.launch {
            _index.value = 0
        }
    }

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

    fun addOrderLine(producto: ProductoModel) {

        tamanoSeleccionado.add(tamanoSelected)

        val productoElegido = ProductoLineaPedidoModel(
            id_producto = producto.id_producto,
            nombre_producto = producto.nombre_producto,
            categoria = producto.categoria.joinToString { it.nombre_categoria },
            tamano = tamanoSelected.tamano,
            pvp =  tamanoSelected.pvp
        )

        val linea = LineaPedidoModel(productoElegido, _cantidad.value)

        lineasPedido.add(linea)
        _lineasTotal.value = lineasPedido.size

        //para controla el estado para engancharlo a la UI
       _listaLineasPedido.value = lineasPedido
    }
}
