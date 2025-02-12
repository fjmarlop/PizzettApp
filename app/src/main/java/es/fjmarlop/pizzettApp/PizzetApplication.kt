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
import es.fjmarlop.pizzettApp.dataBase.local.PizzettAppDB
import es.fjmarlop.pizzettApp.dataBase.local.dao.AddressDao
import es.fjmarlop.pizzettApp.dataBase.local.dao.ProductDao
import es.fjmarlop.pizzettApp.dataBase.local.dao.UserDao
import javax.inject.Singleton

@HiltAndroidApp
class PizzAppliaction: Application() { }

/**
 * Proveer el contexto.
 * **/
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideApplicationContext(@ApplicationContext application: Context): Context {
        return application
    }
}
/** FIN CONTEXTO **/


/**
 * Proveer la base de datos ROOM.
 * */
@Module
@InstallIn(SingletonComponent::class)
object DataBaseRoomModule{

    private const val DATABASE_NAME = "pizzettAppDataBase"

    @Provides
    fun providesUserDao(pizzettAppDB: PizzettAppDB): UserDao {
        return pizzettAppDB.userDao()
    }

    @Provides
    fun providesAddressDao(pizzettAppDB: PizzettAppDB): AddressDao {
        return pizzettAppDB.addressDao()
    }

    @Provides
    fun providesProductDao(pizzettAppDB: PizzettAppDB): ProductDao {
        return pizzettAppDB.productDao()
    }

    @Provides
    @Singleton
    fun providePizzettAppDB(@ApplicationContext application: Context): PizzettAppDB {
        return Room.databaseBuilder(application, PizzettAppDB::class.java, DATABASE_NAME).build()
    }
}
/** FIN ROOM **/


