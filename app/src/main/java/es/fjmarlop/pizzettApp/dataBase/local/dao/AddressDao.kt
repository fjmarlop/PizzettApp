package es.fjmarlop.pizzettApp.dataBase.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.fjmarlop.pizzettApp.dataBase.local.entities.AddressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAddress(addressEntity: AddressEntity)

    @Query("Select * from address_table where email= :email")
    fun getAddress(email: String): Flow<List<AddressEntity>>
    @Query("Select * from address_table where email= :email")
    fun getListAddress(email: String):List<AddressEntity>

    @Delete
    suspend fun deleteAddress(addressEntity: AddressEntity)

    @Query("Update address_table set name = :name, address = :address, city = :city, codPostal = :codPostal where id = :id")
    suspend fun updateAddress(
        id: Int,
        name: String,
        address: String,
        city: String,
        codPostal: String
    )

    @Query("Delete from address_table where id = :id")
    suspend fun deleteAddress(id: Int)

}