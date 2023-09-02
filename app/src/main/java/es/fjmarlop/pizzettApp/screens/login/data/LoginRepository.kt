package es.fjmarlop.pizzettApp.screens.login.data

import es.fjmarlop.pizzettApp.core.roomDatabase.dao.UserDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(private val dao: UserDao)  {

}