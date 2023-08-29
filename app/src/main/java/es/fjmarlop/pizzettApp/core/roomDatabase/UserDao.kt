package es.fjmarlop.pizzettApp.core.roomDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.fjmarlop.pizzettApp.entities.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("Select * from user_table")
    suspend fun getUser():UserEntity


    @Query("Select COUNT(*) from user_table")
    fun getUserCount(): Int

    @Query("Delete from user_table")
    suspend fun cleanDataBase()

     @Query("Update user_table Set name = :name where email = :email")
     suspend fun updateName(name: String, email: String)

     @Query("Update user_table Set phone = :phone where email = :email")
     suspend fun updatePhone(phone: String, email: String)
}