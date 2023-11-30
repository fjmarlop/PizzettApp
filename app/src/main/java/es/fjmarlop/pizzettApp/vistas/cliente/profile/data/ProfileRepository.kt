package es.fjmarlop.pizzettApp.vistas.cliente.profile.data

import es.fjmarlop.pizzettApp.dataBase.local.dao.UserDao
import es.fjmarlop.pizzettApp.dataBase.local.entities.UserEntity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repositorio para la gestión de perfiles de usuario.
 *
 * Este repositorio interactúa con la interfaz [UserDao] para realizar operaciones relacionadas con el perfil del usuario.
 *
 * @property dao Instancia de [UserDao] utilizada para acceder a la base de datos de usuarios.
 */
@Singleton
class ProfileRepository @Inject constructor(private val dao: UserDao) {

    /**
     * Limpia la base de datos, eliminando todos los datos de usuario almacenados.
     */
    suspend fun cleanDataBase() {
        dao.cleanDataBase()
    }

    /**
     * Obtiene la cantidad total de usuarios almacenados en la base de datos.
     *
     * @return Número entero que representa la cantidad de usuarios en la base de datos.
     */
    fun getUserCount(): Int {
        return dao.getUserCount()
    }

    /**
     * Obtiene un usuario de la base de datos.
     *
     * @return Instancia de [UserEntity] que representa al usuario obtenido.
     */
    suspend fun getUser(): UserEntity {
        return dao.getUser()
    }
}
