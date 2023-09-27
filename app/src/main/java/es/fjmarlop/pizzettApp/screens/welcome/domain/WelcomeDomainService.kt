package es.fjmarlop.pizzettApp.screens.welcome.domain

import es.fjmarlop.pizzettApp.core.retrofit.models.ProductoModel
import es.fjmarlop.pizzettApp.screens.main.domain.ProductoDomainService
import javax.inject.Inject

class WelcomeDomainService @Inject constructor(
    private val productoDomainService: ProductoDomainService) {

    suspend fun getRecomendados(): List<ProductoModel>{
       return productoDomainService.getProductosParaRecomendados()
    }
}