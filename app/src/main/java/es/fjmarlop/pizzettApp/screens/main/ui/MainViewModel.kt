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
import es.fjmarlop.pizzettApp.models.LineaPedidoModel
import es.fjmarlop.pizzettApp.models.ProductoModel
import es.fjmarlop.pizzettApp.models.TamaniosModel
import es.fjmarlop.pizzettApp.models.roomModels.UserModel
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

    private val _activateButtonAddLine = MutableStateFlow(false)
    val activateButtonAddLine: StateFlow<Boolean> = _activateButtonAddLine

    private var _lineasPedido = MutableStateFlow<List<LineaPedidoModel>>(emptyList())
    val lineasPedido: StateFlow<List<LineaPedidoModel>> = _lineasPedido

    private lateinit var tamanoSelected: TamaniosModel
    private var isTamanoSelected = false

    private val _cantidad = MutableStateFlow(0)
    val cantidad: StateFlow<Int> = _cantidad

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
        // utils.navigateTo(navHostController, Rutas.MainScreen)
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
        //lineasPedido
    }
}
