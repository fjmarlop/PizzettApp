package es.fjmarlop.pizzettApp.screens.welcome.data

import es.fjmarlop.pizzettApp.core.retrofit.apiServices.ProductoApi
import es.fjmarlop.pizzettApp.core.roomDB.dao.ProductDao
import javax.inject.Inject

class WelcomeRepository @Inject constructor(
    private val productoApi: ProductoApi,
    private val productDao: ProductDao
) {

}


