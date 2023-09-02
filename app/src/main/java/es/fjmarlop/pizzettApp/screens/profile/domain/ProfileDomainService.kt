package es.fjmarlop.pizzettApp.screens.profile.domain

import es.fjmarlop.pizzettApp.entities.roomEntities.toUserModel
import es.fjmarlop.pizzettApp.models.UserModel
import es.fjmarlop.pizzettApp.screens.profile.data.ProfileRepository
import javax.inject.Inject

class ProfileDomainService @Inject constructor(private val profileRepository: ProfileRepository) {

    suspend fun cleanDataBase(){
        profileRepository.cleanDataBase()
    }

    fun getUserCount():Int{
        return profileRepository.getUserCount()
    }

    suspend fun getUser():UserModel{
        return profileRepository.getUser().toUserModel()
    }

}