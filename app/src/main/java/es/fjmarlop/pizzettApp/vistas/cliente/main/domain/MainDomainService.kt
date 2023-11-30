package es.fjmarlop.pizzettApp.vistas.cliente.main.domain

import es.fjmarlop.pizzettApp.dataBase.local.entities.toUserModel
import es.fjmarlop.pizzettApp.dataBase.local.models.UserModel
import es.fjmarlop.pizzettApp.dataBase.local.models.toEntity
import es.fjmarlop.pizzettApp.vistas.cliente.main.data.MainRepositoty
import javax.inject.Inject

/**
 * Servicio del dominio principal para la gestión de usuarios.
 *
 * Este servicio utiliza el repositorio principal [MainRepositoty] para realizar operaciones
 * relacionadas con la gestión de usuarios.
 *
 * @property mainRepository Instancia de [MainRepositoty] utilizada para acceder a la lógica principal del usuario.
 */
class MainDomainService @Inject constructor(private val mainRepository: MainRepositoty) {

    /**
     * Agrega un nuevo usuario al sistema.
     *
     * @param userModel Instancia de [UserModel] que representa al nuevo usuario a ser agregado.
     */
    suspend fun addUser(userModel: UserModel) {
        mainRepository.addUser(userModel.toEntity())
    }

    /**
     * Obtiene la cantidad total de usuarios almacenados en el sistema.
     *
     * @return Número entero que representa la cantidad de usuarios en el sistema.
     */
    fun getUserCount(): Int {
        return mainRepository.getUserCount()
    }

    /**
     * Obtiene un usuario del sistema.
     *
     * @return Instancia de [UserModel] que representa al usuario obtenido.
     */
    suspend fun getUser(): UserModel {
        return mainRepository.getUser().toUserModel()
    }
}
