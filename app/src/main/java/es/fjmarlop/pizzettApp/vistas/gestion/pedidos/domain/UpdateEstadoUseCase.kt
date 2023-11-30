package es.fjmarlop.pizzettApp.vistas.gestion.pedidos.domain

import es.fjmarlop.pizzettApp.vistas.gestion.pedidos.data.PedidosGestionRepository
import javax.inject.Inject

/**
 * Caso de uso para actualizar el estado de un pedido.
 *
 * Este caso de uso utiliza el repositorio [PedidosGestionRepository] para actualizar el estado
 * de un pedido existente mediante su identificador.
 *
 * @property repository Instancia de [PedidosGestionRepository] utilizada para acceder a la lógica de gestión de pedidos.
 */
class UpdateEstadoUseCase @Inject constructor(private val repository: PedidosGestionRepository) {

    /**
     * Invoca el caso de uso para actualizar el estado de un pedido mediante su identificador.
     *
     * @param id Identificador del pedido a actualizar.
     * @param estado Nuevo estado del pedido.
     * @return `true` si la actualización del estado es exitosa, `false` en caso de error.
     */
    suspend operator fun invoke(id: Int, estado: String): Boolean {
        return repository.actualizarEstado(id, estado)
    }
}
