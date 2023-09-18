package es.fjmarlop.pizzettApp.screens.welcome.data

import android.util.Log
import es.fjmarlop.pizzettApp.core.retrofit.apiServices.ProductoApi
import es.fjmarlop.pizzettApp.core.retrofit.models.NewProductoModel
import es.fjmarlop.pizzettApp.core.retrofit.models.TamaniosModel
import es.fjmarlop.pizzettApp.core.roomDB.dao.ProductDao
import es.fjmarlop.pizzettApp.core.roomDB.entities.ProductEntity
import es.fjmarlop.pizzettApp.core.roomDB.entities.TamaniosEntity
import es.fjmarlop.pizzettApp.core.roomDB.models.ProductModel
import javax.inject.Inject

class WelcomeRepository @Inject constructor(
    private val productoApi: ProductoApi,
    private val productDao: ProductDao
) {

    suspend fun getAllProductos(): List<NewProductoModel>? {
        runCatching { productoApi.getAllProducts() }
            .onSuccess {
                return it.map { item ->
                    NewProductoModel(
                        id_producto = item.id_producto,
                        nombre_producto = item.nombre_producto,
                        imagen_producto = item.imagen_producto,
                        descripcion = item.descripcion,
                        categoria = item.categoria.map { cat -> cat.nombre_categoria }.toString(),
                        ingredients = item.ingredients.map { ing -> ing.ingredientName },
                        tamanios = item.tamanios.map { tam ->
                            TamaniosModel(
                                id = tam.id,
                                tamano = tam.tamano,
                                pvp = tam.pvp
                            )
                        })
                }
            }
            .onFailure { Log.i("PizzettApp Info", "Error: ${it.message}") }
        return null
    }


    fun insertRoomProducts(productos: List<ProductModel>) {
        productDao.insertRoomProducts(productos.map { item ->
            ProductEntity(
                id_producto = item.id_producto,
                nombre_producto = item.nombre_producto,
                imagen_producto = item.imagen_producto,
                descripcion = item.descripcion,
                categoria = item.categoria,
                ingredients = item.ingredients,
                tamanios = item.tamanios.map { tam -> TamaniosEntity(id = tam.id, tamano = tam.tamano, pvp = tam.pvp) }
            )
        })
    }
}


