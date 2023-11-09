package es.fjmarlop.pizzettApp.vistas.cliente.profile.domain

import es.fjmarlop.pizzettApp.dataBase.local.entities.toUserModel
import es.fjmarlop.pizzettApp.dataBase.local.models.UserModel
import es.fjmarlop.pizzettApp.vistas.cliente.profile.data.ProfileRepository
import javax.inject.Inject

class ProfileDomainService @Inject constructor(private val profileRepository: ProfileRepository) {

    suspend fun cleanDataBase(){
        profileRepository.cleanDataBase()
    }

    fun getUserCount():Int{
        return profileRepository.getUserCount()
    }

    suspend fun getUser(): UserModel {
        return profileRepository.getUser().toUserModel()
    }

}