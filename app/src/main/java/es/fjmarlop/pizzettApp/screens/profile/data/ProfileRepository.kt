package es.fjmarlop.pizzettApp.screens.profile.data

import es.fjmarlop.pizzettApp.core.roomDatabase.UserDao
import es.fjmarlop.pizzettApp.entities.UserEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(private val dao: UserDao) {

    suspend fun cleanDataBase(){
        dao.cleanDataBase()
    }
    suspend fun addUser(userEntity: UserEntity){
        dao.insertUser(userEntity)
    }
    fun getUserCount():Int{
        return dao.getUserCount()
    }

}