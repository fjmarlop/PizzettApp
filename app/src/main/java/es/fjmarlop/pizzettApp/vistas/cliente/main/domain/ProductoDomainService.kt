package es.fjmarlop.pizzettApp.vistas.cliente.main.domain

import es.fjmarlop.pizzettApp.dataBase.Remote.models.ProductoModel
import es.fjmarlop.pizzettApp.vistas.cliente.main.data.ProductoRepository
import javax.inject.Inject

class ProductoDomainService @Inject constructor(private val productoRepository: ProductoRepository) {

    suspend fun getProductosPorCategoria(cat: String): List<ProductoModel>{
        return productoRepository.getProductosPorCategoria(cat) ?: emptyList()
    }

    suspend fun getProductosParaRecomendados(): List<ProductoModel>{
        return productoRepository.getProductosParaRecomendados() ?: emptyList()
    }

}