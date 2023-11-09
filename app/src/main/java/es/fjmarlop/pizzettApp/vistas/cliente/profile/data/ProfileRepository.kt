package es.fjmarlop.pizzettApp.vistas.cliente.profile.data

import es.fjmarlop.pizzettApp.dataBase.local.dao.UserDao
import es.fjmarlop.pizzettApp.dataBase.local.entities.UserEntity
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