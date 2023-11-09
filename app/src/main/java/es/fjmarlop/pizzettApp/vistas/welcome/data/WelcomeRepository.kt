package es.fjmarlop.pizzettApp.vistas.welcome.data

import android.util.Log
import es.fjmarlop.pizzettApp.dataBase.Remote.apiServices.EmpleadoApi
import javax.inject.Inject

class WelcomeRepository @Inject constructor(
    private val empleadoApi: EmpleadoApi)
{
    suspend fun comprobarEmpleado(email: String): Int {
        runCatching {empleadoApi.comprobarEmpleado(email) }
            .onSuccess { return it }
            .onFailure {
                Log.i("PizzApp Info", "Error: ${it.message}")
                return  -1
            }
        return 0
    }
}



