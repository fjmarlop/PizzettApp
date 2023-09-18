package es.fjmarlop.pizzettApp.screens.main.domain

import es.fjmarlop.pizzettApp.core.retrofit.models.ProductoModel
import es.fjmarlop.pizzettApp.screens.main.data.ProductoRepository
import javax.inject.Inject

class ProductoDomainService @Inject constructor(private val productoRepository: ProductoRepository) {


    suspend fun getProductosPorCategoria(cat: String): List<ProductoModel>{
        return productoRepository.getProductosPorCategoria(cat)
    }

}