package es.fjmarlop.pizzettApp.dataBase.Remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.fjmarlop.pizzettApp.dataBase.Remote.apiServices.EmpleadoApi
import es.fjmarlop.pizzettApp.dataBase.Remote.apiServices.PedidoApi
import es.fjmarlop.pizzettApp.dataBase.Remote.apiServices.ProductoApi
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

    private const val baseUrl = "http://192.168.0.171:8080/"      // Win localhost
    //private const val baseUrl = "http://192.168.0.26:8080/"     // Mac localhost
    //private const val  baseUrl = " https://pizzappi.fly.dev:8080/" //API GOOGLE CLOUD

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

    @Singleton
    @Provides
    fun provideEmpeladoDao(retrofit: Retrofit): EmpleadoApi {
        return retrofit.create(EmpleadoApi::class.java)
    }
    @Singleton
    @Provides
    fun providePedidoDao(retrofit: Retrofit): PedidoApi {
        return retrofit.create(PedidoApi::class.java)
    }

}