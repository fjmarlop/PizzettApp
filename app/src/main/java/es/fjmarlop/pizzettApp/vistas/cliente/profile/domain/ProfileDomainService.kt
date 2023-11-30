package es.fjmarlop.pizzettApp.vistas.cliente.profile.domain

import es.fjmarlop.pizzettApp.dataBase.local.entities.toUserModel
import es.fjmarlop.pizzettApp.dataBase.local.models.UserModel
import es.fjmarlop.pizzettApp.vistas.cliente.profile.data.ProfileRepository
import javax.inject.Inject

/**
 * Servicio del dominio para la gestión de perfiles de usuario.
 *
 * Este servicio utiliza el repositorio de perfiles [ProfileRepository] para realizar operaciones
 * relacionadas con el perfil del usuario.
 *
 * @property profileRepository Instancia de [ProfileRepository] utilizada para acceder a la lógica del dominio de perfiles.
 */
class ProfileDomainService @Inject constructor(private val profileRepository: ProfileRepository) {

    /**
     * Limpia la base de datos de perfiles, eliminando todos los datos de usuario almacenados.
     */
    suspend fun cleanDataBase() {
        profileRepository.cleanDataBase()
    }

    /**
     * Obtiene la cantidad total de perfiles de usuarios almacenados en la base de datos.
     *
     * @return Número entero que representa la cantidad de perfiles de usuarios en la base de datos.
     */
    fun getUserCount(): Int {
        return profileRepository.getUserCount()
    }

    /**
     * Obtiene un perfil de usuario de la base de datos.
     *
     * @return Instancia de [UserModel] que representa al perfil de usuario obtenido.
     */
    suspend fun getUser(): UserModel {
        return profileRepository.getUser().toUserModel()
    }
}
