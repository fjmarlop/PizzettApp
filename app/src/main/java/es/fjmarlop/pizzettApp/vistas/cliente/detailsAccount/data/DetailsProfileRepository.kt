package es.fjmarlop.pizzettApp.vistas.cliente.detailsAccount.data

import es.fjmarlop.pizzettApp.dataBase.local.dao.UserDao
import es.fjmarlop.pizzettApp.dataBase.local.entities.UserEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailsProfileRepository @Inject constructor(private val dao: UserDao) {

    suspend fun getUser(): UserEntity {
        return dao.getUser()
    }

    suspend fun updateUserName(name: String, email: String){
        dao.updateName(name, email)
    }

    suspend fun updatePhone(phone: String, email: String){
        dao.updatePhone(phone, email)
    }

}