package es.fjmarlop.pizzettApp.Test

import es.fjmarlop.pizzettApp.dataBase.Remote.models.PedidoModel
import es.fjmarlop.pizzettApp.vistas.cliente.compra.data.CompraRespository
import es.fjmarlop.pizzettApp.vistas.cliente.compra.domain.FinalizarPedidoUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class FinalizarPedidoUseCaseTest {

    @RelaxedMockK
    private lateinit var compraRespository: CompraRespository

    lateinit var finalizarPedido: FinalizarPedidoUseCase
    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        finalizarPedido = FinalizarPedidoUseCase(compraRespository)
    }
    @Test
    fun `Finalizar pedido con exito`() = runBlocking {
        coEvery { compraRespository.finalizarPedido(any()) } returns 1

        finalizarPedido(
            PedidoModel(
                0, "", "", "",
                "", "", "", 0.0, "", "", emptyList()
            )
        )

        coVerify(exactly = 1) { compraRespository.finalizarPedido(any()) }
    }


    @Test
    fun `Finalizar pedido fallido`() = runBlocking {
        coEvery { compraRespository.finalizarPedido(any()) } returns 0

        finalizarPedido(
            PedidoModel(
                0, "", "", "",
                "", "", "", 0.0, "", "", emptyList()
            )
        )

        coVerify(exactly = 1) { compraRespository.finalizarPedido(any()) }
    }
}