package es.fjmarlop.pizzettApp.vistas.cliente.main.domain

import es.fjmarlop.pizzettApp.dataBase.Remote.models.ProductoModel
import es.fjmarlop.pizzettApp.vistas.cliente.main.data.ProductoRepository
import javax.inject.Inject

/**
 * Servicio del dominio para la gestión de productos.
 *
 * Este servicio utiliza el repositorio de productos [ProductoRepository] para realizar operaciones
 * relacionadas con la obtención de productos por categoría y productos recomendados.
 *
 * @property productoRepository Instancia de [ProductoRepository] utilizada para acceder a la lógica del dominio de productos.
 */
class ProductoDomainService @Inject constructor(private val productoRepository: ProductoRepository) {

    /**
     * Obtiene la lista de productos por categoría.
     *
     * @param cat Categoría de productos a consultar.
     * @return Lista de [ProductoModel] que representa los productos de la categoría especificada.
     */
    suspend fun getProductosPorCategoria(cat: String): List<ProductoModel> {
        return productoRepository.getProductosPorCategoria(cat) ?: emptyList()
    }

    /**
     * Obtiene la lista de productos recomendados.
     *
     * @return Lista de [ProductoModel] que representa los productos recomendados.
     */
    suspend fun getProductosParaRecomendados(): List<ProductoModel> {
        return productoRepository.getProductosParaRecomendados() ?: emptyList()
    }
}
