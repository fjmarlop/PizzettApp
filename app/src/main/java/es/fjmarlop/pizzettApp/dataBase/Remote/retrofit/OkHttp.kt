package es.fjmarlop.pizzettApp.dataBase.Remote.retrofit

import android.content.Context
import es.fjmarlop.pizzettApp.dataBase.Remote.retrofit.interceptors.CacheInterceptor
import es.fjmarlop.pizzettApp.dataBase.Remote.retrofit.interceptors.ForceCacheInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import javax.inject.Inject

class OkHttp @Inject constructor(private val context: Context){
    fun okHttpClient() : OkHttpClient {
        val cacheSize = (10 *1024 * 1024).toLong()
        val myCache = Cache(context.cacheDir, cacheSize)
        return OkHttpClient().newBuilder()
            .addNetworkInterceptor(CacheInterceptor())
            .addInterceptor(ForceCacheInterceptor())
            .cache(myCache)
            .build()

    }
}


