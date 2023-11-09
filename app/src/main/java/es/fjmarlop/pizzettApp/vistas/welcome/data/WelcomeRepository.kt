package es.fjmarlop.pizzettApp.vistas.welcome.data

import es.fjmarlop.pizzettApp.dataBase.Remote.retrofit.apiServices.ProductoApi
import es.fjmarlop.pizzettApp.dataBase.local.roomDB.dao.ProductDao
import javax.inject.Inject

class WelcomeRepository @Inject constructor(
    private val productoApi: ProductoApi,
    private val productDao: ProductDao
) {

}


