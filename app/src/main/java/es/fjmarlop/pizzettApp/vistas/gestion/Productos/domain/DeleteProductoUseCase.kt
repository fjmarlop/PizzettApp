package es.fjmarlop.pizzettApp.vistas.gestion.Productos.domain

import es.fjmarlop.pizzettApp.vistas.gestion.Productos.data.ProductosGestionRepository
import javax.inject.Inject

class DeleteProductoUseCase @Inject constructor(private val repository: ProductosGestionRepository) {

    suspend operator fun invoke(id: Int): Int {
        return repository.borrarProducto(id)
    }
}