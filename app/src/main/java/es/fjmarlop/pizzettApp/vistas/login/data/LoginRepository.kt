package es.fjmarlop.pizzettApp.vistas.login.data

import android.util.Log
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.dataBase.Remote.retrofit.apiServices.EmpleadoApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class LoginRepository @Inject constructor(
    private val empleadoApi: EmpleadoApi,
    private val utils: Utils
) {
    suspend fun comprobarEmpleado(email: String): Int {
        val token = withContext(Dispatchers.IO) { utils.getToken() }
        runCatching {empleadoApi.comprobarEmpleado("Bearer $token", email) }
            .onSuccess { return it }
            .onFailure { Log.i("PizzettApp Info", "Error: ${it.message}") }
      return 0
    }

}