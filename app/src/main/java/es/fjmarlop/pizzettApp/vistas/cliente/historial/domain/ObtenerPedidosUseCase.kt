package es.fjmarlop.pizzettApp.vistas.cliente.historial.domain

import es.fjmarlop.pizzettApp.dataBase.Remote.models.PedidoModel
import es.fjmarlop.pizzettApp.vistas.cliente.historial.data.HistoricoRepository
import javax.inject.Inject

/**
 * Caso de uso para obtener la lista de pedidos de un usuario.
 *
 * Este caso de uso utiliza el repositorio [HistoricoRepository] para obtener información sobre
 * los pedidos realizados por un usuario específico.
 *
 * @property historicoRepository Instancia de [HistoricoRepository] utilizada para acceder a la lógica del histórico de pedidos.
 */
class ObtenerPedidosUseCase @Inject constructor(
    private val historicoRepository: HistoricoRepository) {

    /**
     * Invoca el caso de uso para obtener la lista de pedidos de un usuario.
     *
     * @param email Correo electrónico del usuario cuyos pedidos se van a obtener.
     * @return Lista de [PedidoModel] que representa los pedidos del usuario.
     */
    suspend operator fun invoke(email: String): List<PedidoModel> {
        return historicoRepository.obtnerPedidos(email)
    }

}