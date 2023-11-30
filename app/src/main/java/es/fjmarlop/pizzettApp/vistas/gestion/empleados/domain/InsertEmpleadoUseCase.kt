package es.fjmarlop.pizzettApp.vistas.gestion.empleados.domain

import es.fjmarlop.pizzettApp.dataBase.Remote.models.EmpleadoModel
import es.fjmarlop.pizzettApp.vistas.gestion.empleados.data.EmpleadosGestionRepository
import javax.inject.Inject

/**
 * Caso de uso para insertar un nuevo empleado.
 *
 * Este caso de uso utiliza el repositorio [EmpleadosGestionRepository] para insertar un nuevo empleado
 * en la base de datos.
 *
 * @property repository Instancia de [EmpleadosGestionRepository] utilizada para acceder a la lógica de gestión de empleados.
 */
class InsertEmpleadoUseCase @Inject constructor(private val repository: EmpleadosGestionRepository) {

    /**
     * Invoca el caso de uso para insertar un nuevo empleado en la base de datos.
     *
     * @param empleado Instancia de [EmpleadoModel] que representa al nuevo empleado.
     * @return Código de resultado que indica el éxito de la operación (1 si es exitoso, 0 si hay un error).
     */
    suspend operator fun invoke(empleado: EmpleadoModel): Int {
        return repository.insertarEmpleado(empleado)
    }
}
