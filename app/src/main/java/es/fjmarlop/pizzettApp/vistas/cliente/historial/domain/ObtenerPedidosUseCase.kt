package es.fjmarlop.pizzettApp.vistas.cliente.historial.domain

import es.fjmarlop.pizzettApp.dataBase.Remote.models.PedidoModel
import es.fjmarlop.pizzettApp.vistas.cliente.historial.data.HistoricoRepository
import javax.inject.Inject

class ObtenerPedidosUseCase @Inject constructor(
    private val historicoRepository: HistoricoRepository) {

    suspend operator fun invoke(email: String): List<PedidoModel> {
        return historicoRepository.obtnerPedidos(email)
    }

}