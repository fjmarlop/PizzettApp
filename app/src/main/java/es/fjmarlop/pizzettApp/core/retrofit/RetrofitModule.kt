package es.fjmarlop.pizzettApp.core.retrofit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.fjmarlop.pizzettApp.core.retrofit.apiServices.ProductoApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author Fco Javier Marmolejo López
 *
 * Módulo que carga Retrofit, biblioteca de peticiones Http
 *
 **/

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    //private val baseUrl = "http://192.168.0.171:8080/"      // Win localhost
    //private val baseUrl = "http://192.168.0.26:8080/"     // Mac localhost
    private val baseUrl = "https://pizzettapp.ew.r.appspot.com"

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideHttClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Singleton
    @Provides
    fun provideProductoDao(retrofit: Retrofit): ProductoApi {
        return retrofit.create(ProductoApi::class.java)
    }


}