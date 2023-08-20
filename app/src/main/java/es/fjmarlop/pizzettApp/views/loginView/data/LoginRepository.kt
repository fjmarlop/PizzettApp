package es.fjmarlop.pizzettApp.views.loginView.data

import es.fjmarlop.pizzettApp.core.roomDatabase.PizzettAppDao
import es.fjmarlop.pizzettApp.entity.UserEntity
import es.fjmarlop.pizzettApp.models.UserModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(private val dao: PizzettAppDao) {

    suspend fun addUser(userModel: UserModel) {
        dao.addUser(UserEntity(userModel.email))
    }

}