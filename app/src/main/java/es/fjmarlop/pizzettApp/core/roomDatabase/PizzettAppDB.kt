package es.fjmarlop.pizzettApp.core.roomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import es.fjmarlop.pizzettApp.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class PizzettAppDB() : RoomDatabase() {
    abstract fun pizzettAppDao(): PizzettAppDao
}