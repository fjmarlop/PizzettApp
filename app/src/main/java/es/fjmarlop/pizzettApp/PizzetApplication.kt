package es.fjmarlop.pizzettApp

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.fjmarlop.pizzettApp.core.roomDatabase.PizzettAppDB
import es.fjmarlop.pizzettApp.core.roomDatabase.PizzettAppDao
import javax.inject.Singleton

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


@Module
@InstallIn(SingletonComponent::class)
object DataBaseRoomModule{

    @Provides
    fun providesPizzettAppDao(pizzettAppDB: PizzettAppDB):PizzettAppDao{
        return pizzettAppDB.pizzettAppDao()
    }
    @Provides
    @Singleton
    fun providePizzettAppDB(@ApplicationContext application: Context): PizzettAppDB{
        return Room.databaseBuilder(application, PizzettAppDB::class.java,"pizzettAppDataBase").build()
    }
}



