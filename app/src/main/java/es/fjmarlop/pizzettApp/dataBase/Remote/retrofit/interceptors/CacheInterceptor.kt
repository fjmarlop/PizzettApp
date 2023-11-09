package es.fjmarlop.pizzettApp.dataBase.Remote.retrofit.interceptors

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CacheInterceptor @Inject constructor(): Interceptor{
    /**
     * En la clase CacheInterceptor definimos en el response los criterios de cache.
     * Las cabeceras de cache de la petición que vamos a realizar.
     *
     * 28/09/23
     * De momento tanto CacheInterceptor y ForceCacheInterceptor no funcionan correctamente
     * o relentizan la conexión con la API. ESTAN DESACTIVADOS ACTUALMENTE.
     * **/

    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response = chain.proceed(chain.request())
        val cacheControl = CacheControl.Builder()
            .maxStale(10, TimeUnit.HOURS)
            .onlyIfCached()
            .build()
        return response.newBuilder()
            .header("Cache-Control", cacheControl.toString())
            .build()
    }

}