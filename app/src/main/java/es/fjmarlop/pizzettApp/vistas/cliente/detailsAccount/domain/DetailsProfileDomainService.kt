package es.fjmarlop.pizzettApp.vistas.cliente.detailsAccount.domain

import es.fjmarlop.pizzettApp.dataBase.local.roomDB.entities.toUserModel
import es.fjmarlop.pizzettApp.dataBase.local.roomDB.models.UserModel
import es.fjmarlop.pizzettApp.vistas.cliente.detailsAccount.data.DetailsProfileRepository
import javax.inject.Inject

class DetailsProfileDomainService @Inject constructor(private val repository: DetailsProfileRepository) {


    suspend fun getUser(): UserModel {
        val userEntity = repository.getUser()
        return userEntity.toUserModel()
    }

    suspend fun updateUserName(name: String, email: String) {
        if (!name.isNullOrEmpty() && !email.isNullOrEmpty()) {
            repository.updateUserName(name, email)
        }
    }

    suspend fun updatePhone(phone: String, email: String) {
        if (!phone.isNullOrEmpty() && !email.isNullOrEmpty()) {
            repository.updatePhone(phone, email)
        }
    }

}