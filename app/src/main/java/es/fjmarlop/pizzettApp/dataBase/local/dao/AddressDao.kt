package es.fjmarlop.pizzettApp.dataBase.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.fjmarlop.pizzettApp.dataBase.local.entities.AddressEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Francisco Javier Marmolejo López
 * Las interfaces DAO contenen los metodos para interactuar con la base de datos.
 * */

@Dao
interface AddressDao {

    /**
     * Función para insertar una nueva dirección
     * @param addressEntity: Direccion a insertar
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAddress(addressEntity: AddressEntity)

    /**
     * Función para obtener una dirección por su email
     * @param email: Email del usuario
     * @return Lista de direcciones
     * */
    @Query("Select * from address_table where email= :email")
    fun getAddress(email: String): Flow<List<AddressEntity>>

    /**
     * Función para obtener una dirección por su email
     * @param email: Email del usuario
     * @return Lista de direcciones
     * */
    @Query("Select * from address_table where email= :email")
    suspend fun getListAddress(email: String):List<AddressEntity>

    /**
     * Función para borrar una dirección
     * @param addressEntity: Direccion a borrar
     * */
    @Delete
    suspend fun deleteAddress(addressEntity: AddressEntity)

    /**
     * Función para actualizar una dirección
     * @param id: Id de la direccion
     * @param name: Nombre de la direccion
     * @param address: cuerpo de la direccion
     * @param city: Ciudad
     * @param codPostal: Codigo postal
     * */
    @Query("Update address_table set name = :name, address = :address, city = :city, codPostal = :codPostal where id = :id")
    suspend fun updateAddress(
        id: Int,
        name: String,
        address: String,
        city: String,
        codPostal: String
    )

    /**
     * Función para borrar una dirección por su id
     * @param id: Id de la direccion
     * */
    @Query("Delete from address_table where id = :id")
    suspend fun deleteAddress(id: Int)

}