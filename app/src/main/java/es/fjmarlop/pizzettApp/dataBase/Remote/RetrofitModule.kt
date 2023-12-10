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
 * Módulo Dagger que proporciona instancias de objetos relacionados con Retrofit.
 * Biblioteca de peticiones Http
 * @see SingletonComponent
 */
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    //private const val baseUrl = "http://192.168.0.171:8080/"      // Win localhost
    //private const val baseUrl = "http://192.168.0.26:8080/"     // Mac localhost
    private const val baseUrl = "https://pizzettapapi.lm.r.appspot.com/" //API GOOGLE CLOUD


    /**
     * Proporciona una instancia de Retrofit.
     *
     * @param okHttpClient Cliente HTTP utilizado por Retrofit.
     * @return Instancia de Retrofit configurada con la URL base, el cliente HTTP y el convertidor Gson.
     */
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Proporciona una instancia de OkHttpClient.
     *
     * @return Instancia de OkHttpClient con un interceptor para el registro de solicitudes y respuestas.
     */
    @Provides
    @Singleton
    fun provideHttClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    /**
     * Proporciona una instancia de la interfaz ProductoApi.
     *
     * @param retrofit Instancia de Retrofit utilizada para crear la interfaz.
     * @return Implementación de ProductoApi generada por Retrofit.
     */
    @Singleton
    @Provides
    fun provideProductoDao(retrofit: Retrofit): ProductoApi {
        return retrofit.create(ProductoApi::class.java)
    }

    /**
     * Proporciona una instancia de la interfaz EmpleadoApi.
     *
     * @param retrofit Instancia de Retrofit utilizada para crear la interfaz.
     * @return Implementación de EmpleadoApi generada por Retrofit.
     */
    @Singleton
    @Provides
    fun provideEmpleadoDao(retrofit: Retrofit): EmpleadoApi {
        return retrofit.create(EmpleadoApi::class.java)
    }

    /**
     * Proporciona una instancia de la interfaz PedidoApi.
     *
     * @param retrofit Instancia de Retrofit utilizada para crear la interfaz.
     * @return Implementación de PedidoApi generada por Retrofit.
     */
    @Singleton
    @Provides
    fun providePedidoDao(retrofit: Retrofit): PedidoApi {
        return retrofit.create(PedidoApi::class.java)
    }
}
