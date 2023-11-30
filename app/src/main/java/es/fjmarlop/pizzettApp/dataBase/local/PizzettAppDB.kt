package es.fjmarlop.pizzettApp.dataBase.local

import androidx.room.Database
import androidx.room.RoomDatabase
import es.fjmarlop.pizzettApp.dataBase.local.dao.AddressDao
import es.fjmarlop.pizzettApp.dataBase.local.dao.ProductDao
import es.fjmarlop.pizzettApp.dataBase.local.dao.UserDao
import es.fjmarlop.pizzettApp.dataBase.local.entities.AddressEntity
import es.fjmarlop.pizzettApp.dataBase.local.entities.UserEntity
/**
 * Clase que representa la base de datos de la aplicación PizzettApp utilizando Room.
 *
 * @property userDao DAO para interactuar con la entidad [UserEntity] en la base de datos.
 * @property addressDao DAO para interactuar con la entidad [AddressEntity] en la base de datos.
 * @property productDao DAO para interactuar con las entidades relacionadas con productos en la base de datos.
 */
@Database(
    entities = [
        UserEntity::class,
        AddressEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class PizzettAppDB : RoomDatabase() {

    /**
     * Obtiene el DAO para interactuar con la entidad [UserEntity] en la base de datos.
     *
     * @return DAO para la entidad [UserEntity].
     */
    abstract fun userDao(): UserDao

    /**
     * Obtiene el DAO para interactuar con la entidad [AddressEntity] en la base de datos.
     *
     * @return DAO para la entidad [AddressEntity].
     */
    abstract fun addressDao(): AddressDao

    /**
     * Obtiene el DAO para interactuar con las entidades relacionadas con productos en la base de datos.
     *
     * @return DAO para las entidades relacionadas con productos.
     */
    abstract fun productDao(): ProductDao
}
