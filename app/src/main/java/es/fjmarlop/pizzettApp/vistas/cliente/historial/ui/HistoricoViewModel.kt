package es.fjmarlop.pizzettApp.vistas.cliente.historial.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.dataBase.Remote.models.PedidoModel
import es.fjmarlop.pizzettApp.vistas.cliente.historial.domain.ObtenerPedidosUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoricoViewModel @Inject constructor(
    private val obtenerPedidos: ObtenerPedidosUseCase,

): ViewModel() {


    private val _pedidos = MutableStateFlow<List<PedidoModel>>(emptyList())
    val pedidos: StateFlow<List<PedidoModel>> = _pedidos

    fun getPedidos() {
        viewModelScope.launch(Dispatchers.IO){
            val email = Firebase.auth.currentUser?.email
            _pedidos.value = obtenerPedidos(email!!)
            Log.i("PizzettApp", _pedidos.value.toString())
        }
    }
}