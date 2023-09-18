package es.fjmarlop.pizzettApp.screens.main.domain

import es.fjmarlop.pizzettApp.core.roomDB.entities.toUserModel
import es.fjmarlop.pizzettApp.core.roomDB.models.UserModel
import es.fjmarlop.pizzettApp.core.roomDB.models.toEntity
import es.fjmarlop.pizzettApp.screens.main.data.MainRepositoty
import javax.inject.Inject

class MainDomainService @Inject constructor(private val mainRepository: MainRepositoty) {


    suspend fun addUser(userModel: UserModel){
        mainRepository.addUser(userModel.toEntity())
    }

    fun getUserCount():Int{
        return mainRepository.getUserCount()
    }

    suspend fun getUser(): UserModel {
        return mainRepository.getUser().toUserModel()
    }
}