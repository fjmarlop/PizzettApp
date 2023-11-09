package es.fjmarlop.pizzettApp.dataBase.local

import androidx.room.Database
import androidx.room.RoomDatabase
import es.fjmarlop.pizzettApp.dataBase.local.dao.AddressDao
import es.fjmarlop.pizzettApp.dataBase.local.dao.ProductDao
import es.fjmarlop.pizzettApp.dataBase.local.dao.UserDao
import es.fjmarlop.pizzettApp.dataBase.local.entities.AddressEntity
import es.fjmarlop.pizzettApp.dataBase.local.entities.UserEntity

/**
 * @author Fco Javier Marmolejo López
 *
 * Clase abstracta para crear la base de datos interna con room.
 *
 * */

@Database(
    entities = [
        UserEntity::class,
        AddressEntity::class,
    ], version = 1
)//exportSchema = false  falta por agregar
abstract class PizzettAppDB() : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun addressDao(): AddressDao

    abstract fun productDao(): ProductDao
}