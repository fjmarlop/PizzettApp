package es.fjmarlop.pizzettApp.vistas.cliente.main.data

import es.fjmarlop.pizzettApp.dataBase.local.dao.UserDao
import es.fjmarlop.pizzettApp.dataBase.local.entities.UserEntity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repositorio principal para la gestión de usuarios.
 *
 * Este repositorio interactúa con la interfaz [UserDao] para realizar operaciones relacionadas con la base de datos de usuarios.
 *
 * @property userDao Instancia de [UserDao] utilizada para acceder a la base de datos de usuarios.
 */
@Singleton
class MainRepositoty @Inject constructor(private val userDao: UserDao) {

    /**
     * Agrega un nuevo usuario a la base de datos.
     *
     * @param userEntity Instancia de [UserEntity] que representa al nuevo usuario a ser insertado.
     */
    suspend fun addUser(userEntity: UserEntity) {
        userDao.insertUser(userEntity)
    }

    /**
     * Obtiene la cantidad total de usuarios almacenados en la base de datos.
     *
     * @return Número entero que representa la cantidad de usuarios en la base de datos.
     */
    fun getUserCount(): Int {
        return userDao.getUserCount()
    }

    /**
     * Obtiene un usuario de la base de datos.
     *
     * @return Instancia de [UserEntity] que representa al usuario obtenido.
     */
    suspend fun getUser(): UserEntity {
        return userDao.getUser()
    }
}
