package es.fjmarlop.pizzettApp.vistas.gestion.Productos.domain

import es.fjmarlop.pizzettApp.dataBase.Remote.models.ProductoModel
import es.fjmarlop.pizzettApp.vistas.gestion.Productos.data.ProductosGestionRepository
import javax.inject.Inject

class AddProductoUseCase @Inject constructor(private val repository: ProductosGestionRepository) {

    suspend operator fun invoke(producto: ProductoModel): Int {
       return repository.addProducto(producto)
    }
}