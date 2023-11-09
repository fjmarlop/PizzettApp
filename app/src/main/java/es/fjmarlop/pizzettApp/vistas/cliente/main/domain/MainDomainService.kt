package es.fjmarlop.pizzettApp.vistas.cliente.main.domain

import es.fjmarlop.pizzettApp.dataBase.local.roomDB.entities.toUserModel
import es.fjmarlop.pizzettApp.dataBase.local.roomDB.models.UserModel
import es.fjmarlop.pizzettApp.dataBase.local.roomDB.models.toEntity
import es.fjmarlop.pizzettApp.vistas.cliente.main.data.MainRepositoty
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