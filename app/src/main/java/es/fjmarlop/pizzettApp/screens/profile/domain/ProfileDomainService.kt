package es.fjmarlop.pizzettApp.screens.profile.domain

import es.fjmarlop.pizzettApp.models.UserModel
import es.fjmarlop.pizzettApp.models.toEntity
import es.fjmarlop.pizzettApp.screens.profile.data.ProfileRepository
import javax.inject.Inject

class ProfileDomainService @Inject constructor(private val profileRepository: ProfileRepository) {

    suspend fun cleanDataBase(){
        profileRepository.cleanDataBase()
    }

    suspend fun addUser(userModel: UserModel){
        profileRepository.addUser(userModel.toEntity())
    }

    fun getUserCount():Int{
        return profileRepository.getUserCount()
    }
}