package es.fjmarlop.pizzettApp.dataBase.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.fjmarlop.pizzettApp.dataBase.local.entities.UserEntity

/**
 * Interfaz DAO (Data Access Object) que define los métodos para interactuar con la base de datos de usuarios.
 *
 * Esta interfaz proporciona operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para la entidad de usuario.
 *
 * @author Francisco Javier Marmolejo López
 */
@Dao
interface UserDao {

    /**
     * Inserta un nuevo usuario en la base de datos.
     *
     * @param user Usuario a insertar en la base de datos.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    /**
     * Recupera todos los usuarios de la base de datos.
     *
     * @return Usuario almacenado en la base de datos.
     */
    @Query("SELECT * FROM user_table")
    suspend fun getUser(): UserEntity

    /**
     * Obtiene el número total de usuarios almacenados en la base de datos.
     *
     * @return Número total de usuarios en la base de datos.
     */
    @Query("SELECT COUNT(*) FROM user_table")
    fun getUserCount(): Int

    /**
     * Elimina todos los usuarios de la base de datos, limpiando la tabla.
     */
    @Query("DELETE FROM user_table")
    suspend fun cleanDataBase()

    /**
     * Actualiza el nombre de un usuario en la base de datos.
     *
     * @param name Nuevo nombre del usuario.
     * @param email Correo electrónico del usuario cuyo nombre se va a actualizar.
     */
    @Query("UPDATE user_table SET name = :name WHERE email = :email")
    suspend fun updateName(name: String, email: String)

    /**
     * Actualiza el número de teléfono de un usuario en la base de datos.
     *
     * @param phone Nuevo número de teléfono del usuario.
     * @param email Correo electrónico del usuario cuyo número de teléfono se va a actualizar.
     */
    @Query("UPDATE user_table SET phone = :phone WHERE email = :email")
    suspend fun updatePhone(phone: String, email: String)
}
