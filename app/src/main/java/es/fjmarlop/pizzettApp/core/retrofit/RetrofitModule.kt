package es.fjmarlop.pizzettApp.core.retrofit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.fjmarlop.pizzettApp.core.retrofit.dao.PizzaDao
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    private val baseUrl = "http://192.168.0.171:8080/"
    //private val baseUrl = "http://192.168.0.26:8080/"

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


}