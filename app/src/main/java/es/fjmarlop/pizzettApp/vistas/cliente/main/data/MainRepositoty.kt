package es.fjmarlop.pizzettApp.vistas.cliente.main.data

import es.fjmarlop.pizzettApp.dataBase.local.dao.UserDao
import es.fjmarlop.pizzettApp.dataBase.local.entities.UserEntity
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