package es.fjmarlop.pizzettApp.vistas.cliente.detailsAccount.data

import es.fjmarlop.pizzettApp.dataBase.local.dao.UserDao
import es.fjmarlop.pizzettApp.dataBase.local.entities.UserEntity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repositorio para la gestión de detalles de perfil de usuario.
 *
 * Este repositorio se encarga de interactuar con la capa de acceso a datos para obtener
 * y actualizar información relacionada con el perfil de usuario.
 *
 * @property dao Instancia de [UserDao] utilizada para acceder a los datos de usuario.
 */
@Singleton
class DetailsProfileRepository @Inject constructor(private val dao: UserDao) {

    /**
     * Obtiene la información del usuario almacenada en la base de datos.
     *
     * @return Objeto [UserEntity] que representa la información del usuario.
     */
    suspend fun getUser(): UserEntity {
        return dao.getUser()
    }

    /**
     * Actualiza el nombre del usuario en la base de datos.
     *
     * @param name Nuevo nombre del usuario.
     * @param email Correo electrónico del usuario cuyo nombre se va a actualizar.
     */
    suspend fun updateUserName(name: String, email: String) {
        dao.updateName(name, email)
    }

    /**
     * Actualiza el número de teléfono del usuario en la base de datos.
     *
     * @param phone Nuevo número de teléfono del usuario.
     * @param email Correo electrónico del usuario cuyo número de teléfono se va a actualizar.
     */
    suspend fun updatePhone(phone: String, email: String) {
        dao.updatePhone(phone, email)
    }
}