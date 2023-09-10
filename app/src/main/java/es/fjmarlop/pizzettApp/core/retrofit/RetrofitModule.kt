package es.fjmarlop.pizzettApp.core.retrofit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.fjmarlop.pizzettApp.core.retrofit.dao.PizzaDao
import es.fjmarlop.pizzettApp.core.retrofit.dao.ProductoDao
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
class RetrofitModule {

    private val baseUrl = "http://192.168.0.171:8080/"      // Win
    //private val baseUrl = "http://192.168.0.26:8080/"     // Mac

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providePizzaDao(retrofit: Retrofit): PizzaDao {
        return retrofit.create(PizzaDao::class.java)
    }

    @Singleton
    @Provides
    fun provideProductoDao(retrofit: Retrofit): ProductoDao {
        return retrofit.create(ProductoDao::class.java)
    }


}