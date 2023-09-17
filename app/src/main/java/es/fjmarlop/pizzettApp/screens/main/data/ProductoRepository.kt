package es.fjmarlop.pizzettApp.screens.main.data

import es.fjmarlop.pizzettApp.core.retrofit.dao.ProductoDao
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.models.databaseModels.ProductoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class ProductoRepository @Inject constructor(
    private val productoDao: ProductoDao,
    private val utils: Utils
) {

    suspend fun getProductosPorCategoria(cat: String): List<ProductoModel> {
        val token = withContext(Dispatchers.IO) { utils.getToken() }
        return try {
            withContext(Dispatchers.IO) {productoDao.getProductosPorCategoria("Bearer $token", cat) }
        } catch (e:HttpException){
            emptyList()
        }
    }
}