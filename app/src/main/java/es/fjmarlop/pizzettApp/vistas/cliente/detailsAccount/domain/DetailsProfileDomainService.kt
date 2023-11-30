package es.fjmarlop.pizzettApp.vistas.cliente.detailsAccount.domain


import es.fjmarlop.pizzettApp.dataBase.local.entities.toUserModel
import es.fjmarlop.pizzettApp.dataBase.local.models.UserModel
import es.fjmarlop.pizzettApp.vistas.cliente.detailsAccount.data.DetailsProfileRepository
import javax.inject.Inject

/**
 * Servicio del dominio para la gestión de detalles de perfil de usuario.
 *
 * Este servicio proporciona funciones para obtener y actualizar información relacionada con
 * el perfil de usuario, interactuando con el repositorio correspondiente.
 *
 * @property repository Instancia de [DetailsProfileRepository] utilizada para acceder a los datos de perfil.
 */
class DetailsProfileDomainService @Inject constructor(private val repository: DetailsProfileRepository) {

    /**
     * Obtiene la información del usuario en forma de [UserModel].
     *
     * @return Objeto [UserModel] que representa la información del usuario.
     */
    suspend fun getUser(): UserModel {
        val userEntity = repository.getUser()
        return userEntity.toUserModel()
    }

    /**
     * Actualiza el nombre del usuario si tanto el nombre como el correo electrónico no están vacíos.
     *
     * @param name Nuevo nombre del usuario.
     * @param email Correo electrónico del usuario cuyo nombre se va a actualizar.
     */
    suspend fun updateUserName(name: String, email: String) {
        if (name.isNotEmpty() && email.isNotEmpty()) {
            repository.updateUserName(name, email)
        }
    }

    /**
     * Actualiza el número de teléfono del usuario si tanto el número de teléfono como el correo
     * electrónico no están vacíos.
     *
     * @param phone Nuevo número de teléfono del usuario.
     * @param email Correo electrónico del usuario cuyo número de teléfono se va a actualizar.
     */
    suspend fun updatePhone(phone: String, email: String) {
        if (phone.isNotEmpty() && email.isNotEmpty()) {
            repository.updatePhone(phone, email)
        }
    }
}
