package es.fjmarlop.pizzettApp.vistas.gestion.pedidos.domain

import es.fjmarlop.pizzettApp.dataBase.Remote.models.PedidoModel
import es.fjmarlop.pizzettApp.vistas.gestion.pedidos.data.PedidosGestionRepository
import javax.inject.Inject

/**
 * Caso de uso para obtener la lista de todos los pedidos.
 *
 * Este caso de uso utiliza el repositorio [PedidosGestionRepository] para obtener la lista completa
 * de pedidos disponibles.
 *
 * @property repository Instancia de [PedidosGestionRepository] utilizada para acceder a la lógica de gestión de pedidos.
 */
class GetAllPedidosUseCase @Inject constructor(private val repository: PedidosGestionRepository){

    /**
     * Invoca el caso de uso para obtener la lista de todos los pedidos.
     *
     * @return Lista de [PedidoModel] que representa a todos los pedidos.
     */
    suspend operator fun invoke(): List<PedidoModel> = repository.getAllPedidos()

}