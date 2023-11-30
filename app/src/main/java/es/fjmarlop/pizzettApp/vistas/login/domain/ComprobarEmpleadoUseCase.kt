package es.fjmarlop.pizzettApp.vistas.login.domain

import es.fjmarlop.pizzettApp.vistas.login.data.LoginRepository
import javax.inject.Inject

/**
 * Caso de uso para comprobar la existencia de un empleado por correo electrónico.
 *
 * Este caso de uso utiliza el repositorio de inicio de sesión [LoginRepository]
 * para comprobar si un empleado con el correo electrónico especificado existe.
 *
 * @property loginRepository Instancia de [LoginRepository] utilizada para acceder a la lógica de comprobación de empleados.
 */
class ComprobarEmpleadoUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    /**
     * Invoca el caso de uso para comprobar la existencia de un empleado por correo electrónico.
     *
     * @param email Correo electrónico del empleado a comprobar.
     * @return Código de resultado que indica el estado de la comprobación del empleado.
     */
    suspend operator fun invoke(email: String): Int {
        return loginRepository.comprobarEmpleado(email)
    }
}
