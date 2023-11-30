package es.fjmarlop.pizzettApp.vistas.gestion.Productos.domain

import es.fjmarlop.pizzettApp.dataBase.Remote.models.TamaniosModel
import es.fjmarlop.pizzettApp.vistas.gestion.Productos.data.ProductosGestionRepository
import javax.inject.Inject

/**
 * Caso de uso para obtener la lista de todos los tamaños disponibles.
 *
 * Este caso de uso utiliza el repositorio [ProductosGestionRepository] para obtener la lista
 * completa de tamaños disponibles desde la API.
 *
 * @property repository Instancia de [ProductosGestionRepository] utilizada para acceder a la lógica de gestión de productos.
 */
class GetTamanosUseCase @Inject constructor(private val repository: ProductosGestionRepository) {

    /**
     * Invoca el caso de uso para obtener la lista de todos los tamaños disponibles.
     *
     * @return Lista de [TamaniosModel] que representa a todos los tamaños disponibles.
     */
    suspend operator fun invoke(): List<TamaniosModel> = repository.getTamanos()
}
