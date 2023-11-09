package es.fjmarlop.pizzettApp.vistas.login.data

import android.util.Log
import es.fjmarlop.pizzettApp.dataBase.Remote.apiServices.EmpleadoApi
import javax.inject.Inject


class LoginRepository @Inject constructor(
    private val empleadoApi: EmpleadoApi
) {
    suspend fun comprobarEmpleado(email: String): Int {
        runCatching {empleadoApi.comprobarEmpleado( email) }
            .onSuccess { return it }
            .onFailure { Log.i("PizzettApp Info", "Error: ${it.message}") }
      return 0
    }

}