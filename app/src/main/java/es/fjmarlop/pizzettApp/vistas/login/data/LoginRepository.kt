package es.fjmarlop.pizzettApp.vistas.login.data

import android.util.Log
import es.fjmarlop.pizzettApp.dataBase.Remote.apiServices.EmpleadoApi
import javax.inject.Inject

/**
 * Repositorio para la autenticación y comprobación de empleados.
 *
 * Este repositorio interactúa con la API de empleados [EmpleadoApi] para realizar operaciones
 * relacionadas con la autenticación y comprobación de empleados.
 *
 * @property empleadoApi Instancia de [EmpleadoApi] utilizada para realizar operaciones relacionadas con los empleados.
 */
class LoginRepository @Inject constructor(
    private val empleadoApi: EmpleadoApi
) {
    /**
     * Comprueba si un empleado con el correo electrónico especificado existe y devuelve un código de resultado.
     *
     * @param email Correo electrónico del empleado a comprobar.
     * @return Código de resultado que indica el estado de la comprobación del empleado.
     */
    suspend fun comprobarEmpleado(email: String): Int {
        runCatching {empleadoApi.comprobarEmpleado( email) }
            .onSuccess { return it }
            .onFailure { Log.i("PizzettApp Info", "Error: ${it.message}") }
      return 0
    }

}