package es.fjmarlop.pizzettApp.vistas.cliente.compra.domain

import es.fjmarlop.pizzettApp.dataBase.Remote.models.PedidoModel
import es.fjmarlop.pizzettApp.vistas.cliente.compra.data.CompraRespository
import javax.inject.Inject

class FinalizarPedidoUseCase @Inject constructor(private val compraRespository: CompraRespository) {

    /**
     * Finaliza un pedido realizando la inserción en la API.
     *
     * @param pedido Modelo de datos del pedido.
     * @return Código de estado de la operación (1 si tiene éxito, 0 si falla).
     */
    suspend operator fun invoke(pedido: PedidoModel): Int {
        return compraRespository.finalizarPedido(pedido)
    }
}