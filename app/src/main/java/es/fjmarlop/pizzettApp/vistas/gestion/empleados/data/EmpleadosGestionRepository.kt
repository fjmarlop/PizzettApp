package es.fjmarlop.pizzettApp.vistas.gestion.empleados.data

import android.util.Log
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.dataBase.Remote.apiServices.EmpleadoApi
import es.fjmarlop.pizzettApp.dataBase.Remote.models.EmpleadoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repositorio para gestionar operaciones relacionadas con la gestión de empleados.
 *
 * Este repositorio interactúa con la API de empleados [EmpleadoApi] para realizar operaciones
 * como obtener todos los empleados, insertar un nuevo empleado y eliminar un empleado existente.
 *
 * @property empleadoApi Instancia de [EmpleadoApi] utilizada para realizar operaciones relacionadas con los empleados.
 * @property utils Instancia de [Utils] utilizada para funciones de utilidad.
 */
class EmpleadosGestionRepository @Inject constructor(
    private val empleadoApi: EmpleadoApi,
    private val utils: Utils
) {

    /**
     * Obtiene la lista de todos los empleados desde la API.
     *
     * @return Lista de [EmpleadoModel] que representa a todos los empleados.
     */
    suspend fun getAllEmpleados(): List<EmpleadoModel> {
        val token = withContext(Dispatchers.IO) { utils.getToken() }
        runCatching { empleadoApi.getAllEmpleados("Bearer $token") }
            .onSuccess { return it.map { item -> item.toModel() } }
            .onFailure { Log.i("PizzettApp info", "Ha habido un error al obtener los empleos") }
        return emptyList()
    }

    /**
     * Inserta un nuevo empleado utilizando la API.
     *
     * @param empleado Instancia de [EmpleadoModel] que representa al nuevo empleado.
     * @return Código de resultado que indica el éxito de la operación (1 si es exitoso, 0 si hay un error).
     */
    suspend fun insertarEmpleado(empleado: EmpleadoModel): Int {
        val token = withContext(Dispatchers.IO) { utils.getToken() }
        runCatching { empleadoApi.insertarEmpleado("Bearer $token",empleado) }
            .onSuccess { return 1 }
            .onFailure { Log.i("PizzettApp info", "Ha habido un error al insertar el empleado") }
        return 0
    }

    /**
     * Elimina un empleado existente utilizando la API.
     *
     * @param id Identificador del empleado a eliminar.
     * @return Código de resultado que indica el éxito de la operación (1 si es exitoso, 0 si hay un error).
     */
    suspend fun deleteEmpleado(id: Int): Int {
        val token = withContext(Dispatchers.IO) { utils.getToken() }
        runCatching { empleadoApi.deleteEmpleado("Bearer $token",id) }
            .onSuccess { return 1 }
            .onFailure { Log.i("PizzettApp info", "Ha habido un error al borrar el empleado") }
        return 0
    }
}