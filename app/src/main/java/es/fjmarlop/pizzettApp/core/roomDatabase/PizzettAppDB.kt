package es.fjmarlop.pizzettApp.core.roomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import es.fjmarlop.pizzettApp.entities.AddressEntity
import es.fjmarlop.pizzettApp.entities.UserEntity

@Database(entities = [UserEntity::class, AddressEntity::class], version = 1)
abstract class PizzettAppDB() : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun addressDao(): AddressDao
}