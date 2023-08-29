package es.fjmarlop.pizzettApp.screens.detailsAccount.data

import es.fjmarlop.pizzettApp.core.roomDatabase.UserDao
import es.fjmarlop.pizzettApp.entities.UserEntity
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