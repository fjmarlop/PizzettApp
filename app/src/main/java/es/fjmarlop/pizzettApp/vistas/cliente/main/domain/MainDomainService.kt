package es.fjmarlop.pizzettApp.vistas.cliente.main.domain

import es.fjmarlop.pizzettApp.dataBase.local.entities.toUserModel
import es.fjmarlop.pizzettApp.dataBase.local.models.UserModel
import es.fjmarlop.pizzettApp.dataBase.local.models.toEntity
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