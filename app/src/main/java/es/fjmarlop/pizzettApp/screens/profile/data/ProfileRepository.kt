package es.fjmarlop.pizzettApp.screens.profile.data

import es.fjmarlop.pizzettApp.core.roomDB.dao.UserDao
import es.fjmarlop.pizzettApp.core.roomDB.entities.UserEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(private val dao: UserDao) {

    suspend fun cleanDataBase(){
        dao.cleanDataBase()
    }

    fun getUserCount():Int{
        return dao.getUserCount()
    }

    suspend fun getUser(): UserEntity {
        return dao.getUser()
    }

}