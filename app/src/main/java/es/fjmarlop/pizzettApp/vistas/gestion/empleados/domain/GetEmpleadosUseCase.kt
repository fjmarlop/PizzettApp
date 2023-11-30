package es.fjmarlop.pizzettApp.vistas.gestion.empleados.domain

import es.fjmarlop.pizzettApp.dataBase.Remote.models.EmpleadoModel
import es.fjmarlop.pizzettApp.vistas.gestion.empleados.data.EmpleadosGestionRepository
import javax.inject.Inject

/**
 * Caso de uso para obtener la lista de todos los empleados.
 *
 * Este caso de uso utiliza el repositorio [EmpleadosGestionRepository] para obtener la lista completa
 * de empleados disponibles.
 *
 * @property repository Instancia de [EmpleadosGestionRepository] utilizada para acceder a la lógica de gestión de empleados.
 */
class GetEmpleadosUseCase @Inject constructor(private val repository: EmpleadosGestionRepository) {

    /**
     * Invoca el caso de uso para obtener la lista de todos los empleados.
     *
     * @return Lista de [EmpleadoModel] que representa a todos los empleados.
     */
    suspend operator fun invoke(): List<EmpleadoModel> = repository.getAllEmpleados()
}
