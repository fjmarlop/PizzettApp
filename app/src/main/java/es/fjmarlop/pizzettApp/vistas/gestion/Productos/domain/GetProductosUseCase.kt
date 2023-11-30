package es.fjmarlop.pizzettApp.vistas.gestion.Productos.domain

import es.fjmarlop.pizzettApp.dataBase.Remote.models.ProductoModel
import es.fjmarlop.pizzettApp.vistas.gestion.Productos.data.ProductosGestionRepository
import javax.inject.Inject

/**
 * Caso de uso para obtener la lista de todos los productos.
 *
 * Este caso de uso utiliza el repositorio [ProductosGestionRepository] para obtener la lista
 * completa de productos desde la API.
 *
 * @property repository Instancia de [ProductosGestionRepository] utilizada para acceder a la lógica de gestión de productos.
 */
class GetProductosUseCase @Inject constructor(private val repository: ProductosGestionRepository) {

    /**
     * Invoca el caso de uso para obtener la lista de todos los productos.
     *
     * @return Lista de [ProductoModel] que representa a todos los productos.
     */
    suspend operator fun invoke(): List<ProductoModel> {
        return repository.getProductos()
    }
}
