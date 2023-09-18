package es.fjmarlop.pizzettApp.screens.main.data

import es.fjmarlop.pizzettApp.core.retrofit.apiServices.ProductoApi
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.core.retrofit.models.ProductoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class ProductoRepository @Inject constructor(
    private val productoApi: ProductoApi,
    private val utils: Utils
) {

    suspend fun getProductosPorCategoria(cat: String): List<ProductoModel> {
        val token = withContext(Dispatchers.IO) { utils.getToken() }
        return try {
            withContext(Dispatchers.IO) {productoApi.getProductosPorCategoria("Bearer $token", cat) }
        } catch (e:HttpException){
            emptyList()
        }
    }
}