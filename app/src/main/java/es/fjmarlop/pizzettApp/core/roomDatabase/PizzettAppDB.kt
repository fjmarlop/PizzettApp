package es.fjmarlop.pizzettApp.core.roomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import es.fjmarlop.pizzettApp.core.roomDatabase.dao.AddressDao
import es.fjmarlop.pizzettApp.core.roomDatabase.dao.UserDao
import es.fjmarlop.pizzettApp.entities.roomEntities.AddressEntity
import es.fjmarlop.pizzettApp.entities.roomEntities.UserEntity

/**
 * @author Fco Javier Marmolejo López
 *
 * Clase abstracta para crear la base de datos interna con room.
 *
 * */

@Database(entities = [UserEntity::class, AddressEntity::class], version = 1)//exportSchema = false  falta por agregar
abstract class PizzettAppDB() : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun addressDao(): AddressDao
}