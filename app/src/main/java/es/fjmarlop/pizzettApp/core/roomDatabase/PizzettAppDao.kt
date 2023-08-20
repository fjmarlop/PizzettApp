package es.fjmarlop.pizzettApp.core.roomDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import es.fjmarlop.pizzettApp.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PizzettAppDao {

    @Query("Select email from UserEntity")
    fun getEmail():Flow<String>

    @Insert
    fun addUser(item: UserEntity)
}