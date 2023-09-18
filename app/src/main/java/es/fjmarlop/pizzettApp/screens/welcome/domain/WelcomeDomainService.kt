package es.fjmarlop.pizzettApp.screens.welcome.domain

import es.fjmarlop.pizzettApp.core.retrofit.models.NewProductoModel
import es.fjmarlop.pizzettApp.core.roomDB.models.ProductModel
import es.fjmarlop.pizzettApp.screens.welcome.data.WelcomeRepository
import javax.inject.Inject

class WelcomeDomainService @Inject constructor(private val welcomeRepository: WelcomeRepository) {


    suspend fun getAllProductos(): List<NewProductoModel> {
        return welcomeRepository.getAllProductos() ?: emptyList()
    }

    fun insertRoomProducts(productos: List<NewProductoModel>){
        val list = productos.map { item -> ProductModel(
            id_producto = item.id_producto,
            nombre_producto = item.nombre_producto,
            imagen_producto = item.imagen_producto,
            descripcion = item.descripcion,
            categoria = item.categoria,
            ingredients = item.ingredients,
            tamanios = item.tamanios
        ) }
        welcomeRepository.insertRoomProducts(list)
    }
}