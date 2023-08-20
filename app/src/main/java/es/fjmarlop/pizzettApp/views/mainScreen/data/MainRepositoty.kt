package es.fjmarlop.pizzettApp.views.mainScreen.data

import es.fjmarlop.pizzettApp.core.roomDatabase.PizzettAppDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepositoty @Inject constructor(dao: PizzettAppDao) {

    val email: Flow<String> = dao.getEmail()

}