package es.fjmarlop.pizzettApp.dataBase.Remote.interceptors

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class ForceCacheInterceptor @Inject constructor() : Interceptor {
    /**
     * En la clase ForceCacheInterceptor forzamos el uso de la cache si la conexión a internet no está disponible.
     * Es posible añadir otros mecanismos para comprobar si los dispositivos de conexión a internet está habilitados,
     * esto nos permite forzar el uso de la cache si está usando datos móviles.
     * */
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        if (!isInternetAvailable()) {
            builder.cacheControl(CacheControl.FORCE_CACHE)
        }
        return chain.proceed(builder.build())
    }

    private fun isInternetAvailable(): Boolean {
        return try {
            val command = "ping -c 1 google.com"
            Runtime.getRuntime().exec(command).waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }
}