package es.fjmarlop.pizzettApp

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@HiltAndroidApp
class PizzAppliaction: Application() { }

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideApplicationContext(@ApplicationContext application: Context): Context {
        return application
    }
}


