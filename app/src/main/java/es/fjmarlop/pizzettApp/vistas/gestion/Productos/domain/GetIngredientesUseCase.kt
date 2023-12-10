package es.fjmarlop.pizzettApp.vistas.gestion.Productos.domain

import es.fjmarlop.pizzettApp.dataBase.Remote.models.IngredientsModel
import es.fjmarlop.pizzettApp.vistas.gestion.Productos.data.ProductosGestionRepository
import javax.inject.Inject

/**
 * Caso de uso para obtener la lista de nombres de ingredientes disponibles.
 *
 * Este caso de uso utiliza el repositorio [ProductosGestionRepository] para obtener la lista
 * de ingredientes desde la API y extraer los nombres de los ingredientes.
 *
 * @property repository Instancia de [ProductosGestionRepository] utilizada para acceder a la lógica de gestión de productos.
 */
class GetIngredientesUseCase @Inject constructor(private val repository: ProductosGestionRepository) {

    /**
     * Invoca el caso de uso para obtener la lista de nombres de ingredientes disponibles.
     *
     * @return Lista de nombres de ingredientes.
     */
    suspend operator fun invoke(): List<IngredientsModel> {
        return repository.getIngredientes()
    }
}
