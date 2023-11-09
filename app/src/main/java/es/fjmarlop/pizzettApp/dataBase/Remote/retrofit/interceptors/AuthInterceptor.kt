package es.fjmarlop.pizzettApp.dataBase.Remote.retrofit.interceptors

import es.fjmarlop.pizzettApp.core.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val utils: Utils) : Interceptor {

    /**
     * NO FUNCIONA CORRECTAMENTE MANDAR EL TOKEN POR LA CABECERA DE INTERCEPTOR
     * */

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
            .newBuilder()
            .header("Authorization", getToken()).build()

        return chain.proceed(request)
    }

    private fun getToken(): String {
        val token = CoroutineScope(Dispatchers.IO).launch { utils.getToken() }
        return token.toString()
    }


}