package es.fjmarlop.pizzettApp.vistas.welcome.domain

import es.fjmarlop.pizzettApp.vistas.welcome.data.WelcomeRepository
import javax.inject.Inject

/**
 * Caso de uso para comprobar la existencia de un empleado por correo electrónico en la pantalla de bienvenida.
 *
 * Este caso de uso utiliza el repositorio [WelcomeRepository] para comprobar si un empleado
 * con el correo electrónico especificado existe en la pantalla de bienvenida.
 *
 * @property repository Instancia de [WelcomeRepository] utilizada para acceder a la lógica de comprobación de empleados.
 */
class ComprobarEmpleadoUseCase @Inject constructor(
    private val repository: WelcomeRepository
) {

    /**
     * Invoca el caso de uso para comprobar la existencia de un empleado por correo electrónico.
     *
     * @param email Correo electrónico del empleado a comprobar.
     * @return Código de resultado que indica el estado de la comprobación del empleado.
     */
    suspend operator fun invoke(email: String): Int {
        return repository.comprobarEmpleado(email)
    }
}
