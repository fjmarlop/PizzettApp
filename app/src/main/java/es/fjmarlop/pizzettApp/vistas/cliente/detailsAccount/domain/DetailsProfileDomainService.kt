package es.fjmarlop.pizzettApp.vistas.cliente.detailsAccount.domain


import es.fjmarlop.pizzettApp.dataBase.local.entities.toUserModel
import es.fjmarlop.pizzettApp.dataBase.local.models.UserModel
import es.fjmarlop.pizzettApp.vistas.cliente.detailsAccount.data.DetailsProfileRepository
import javax.inject.Inject

class DetailsProfileDomainService @Inject constructor(private val repository: DetailsProfileRepository) {


    suspend fun getUser(): UserModel {
        val userEntity = repository.getUser()
        return userEntity.toUserModel()
    }

    suspend fun updateUserName(name: String, email: String) {
        if (name.isNotEmpty() && email.isNotEmpty()) {
            repository.updateUserName(name, email)
        }
    }

    suspend fun updatePhone(phone: String, email: String) {
        if (phone.isNotEmpty() && email.isNotEmpty()) {
            repository.updatePhone(phone, email)
        }
    }

}