package es.fjmarlop.pizzettApp.Test

import es.fjmarlop.pizzettApp.dataBase.Remote.models.PedidoModel
import es.fjmarlop.pizzettApp.vistas.cliente.historial.data.HistoricoRepository
import es.fjmarlop.pizzettApp.vistas.cliente.historial.domain.ObtenerPedidosUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ObtenerPedidosUseCaseTest {

    @RelaxedMockK
    lateinit var historicoRepository: HistoricoRepository

    lateinit var historicoUseCase: ObtenerPedidosUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        historicoUseCase = ObtenerPedidosUseCase(historicoRepository)
    }

    @Test
    fun `Obtener pedidos con exito`() = runBlocking {

        val pedidos = listOf(PedidoModel(0, "", "", "", "", "", "", 0.0, "", "", emptyList()))
        coEvery { historicoRepository.obtnerPedidos(any()) } returns pedidos

        val email = "fjmarlop.83@gmail.com"
        val response = historicoUseCase(email)

        coVerify(exactly = 1) { historicoRepository.obtnerPedidos(email) }
        assert(response == pedidos)
    }

}
