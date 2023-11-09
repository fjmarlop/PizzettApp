package es.fjmarlop.pizzettApp.vistas.welcome.domain

import es.fjmarlop.pizzettApp.dataBase.Remote.retrofit.models.ProductoModel
import es.fjmarlop.pizzettApp.vistas.cliente.main.domain.ProductoDomainService
import javax.inject.Inject

class WelcomeDomainService @Inject constructor(
    private val productoDomainService: ProductoDomainService
) {

    suspend fun getRecomendados(): List<ProductoModel>{
       return productoDomainService.getProductosParaRecomendados()
    }
}