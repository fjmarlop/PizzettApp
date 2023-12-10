package es.fjmarlop.pizzettApp.vistas.cliente.historial.ui

import android.os.Handler
import android.os.Looper
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

/**
 * ViewModel para la visualización y gestión del histórico de pedidos de un usuario.
 *
 * Este ViewModel utiliza el caso de uso [ObtenerPedidosUseCase] para obtener la lista de pedidos
 * realizados por el usuario actualmente autenticado. La información se actualiza en el flujo de estado [pedidos].
 *
 * @property obtenerPedidos Caso de uso para obtener la lista de pedidos de un usuario.
 */
@HiltViewModel
class HistoricoViewModel @Inject constructor(
    private val obtenerPedidos: ObtenerPedidosUseCase,

): ViewModel() {


    private val _pedidos = MutableStateFlow<List<PedidoModel>>(emptyList())
    val pedidos: StateFlow<List<PedidoModel>> = _pedidos


    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable


    /**
     * Obtiene la lista de pedidos del usuario actualmente autenticado.
     *
     * La información se actualiza en el flujo de estado [pedidos].
     */
    fun getPedidos() {
        viewModelScope.launch(Dispatchers.IO){
            val email = Firebase.auth.currentUser?.email
            _pedidos.value = obtenerPedidos(email!!)
        }
    }

    /**
     *  Método para iniciar la actualización periódica de pedidos.
     */
    fun startUpdating() {
        runnable = object : Runnable {
            override fun run() {
               getPedidos()
                // Programar la próxima ejecución después de x segundos
                handler.postDelayed(this, 10000)
            }
        }
        // Iniciar la actualización periódica
        handler.postDelayed(runnable, 0)
    }
}