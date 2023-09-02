package es.fjmarlop.pizzettApp.screens.main.data

import es.fjmarlop.pizzettApp.core.roomDatabase.dao.UserDao
import es.fjmarlop.pizzettApp.entities.roomEntities.UserEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepositoty @Inject constructor(private val userDao: UserDao) {

    suspend fun addUser(userEntity: UserEntity){
        userDao.insertUser(userEntity)
    }
    fun getUserCount():Int{
        return userDao.getUserCount()
    }

    suspend fun getUser(): UserEntity {
        return userDao.getUser()
    }

}