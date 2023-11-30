package es.fjmarlop.pizzettApp.vistas.gestion.empleados.domain

import es.fjmarlop.pizzettApp.vistas.gestion.empleados.data.EmpleadosGestionRepository
import javax.inject.Inject

/**
 * Caso de uso para eliminar un empleado.
 *
 * Este caso de uso utiliza el repositorio [EmpleadosGestionRepository] para eliminar un empleado
 * existente mediante su identificador.
 *
 * @property repository Instancia de [EmpleadosGestionRepository] utilizada para acceder a la lógica de gestión de empleados.
 */
class DeleteEmpleadoUseCase @Inject constructor(private val repository: EmpleadosGestionRepository) {

    /**
     * Invoca el caso de uso para eliminar un empleado mediante su identificador.
     *
     * @param id Identificador del empleado a eliminar.
     * @return Código de resultado que indica el éxito de la operación (1 si es exitoso, 0 si hay un error).
     */
    suspend operator fun invoke(id: Int): Int {
        return repository.deleteEmpleado(id)
    }
}
