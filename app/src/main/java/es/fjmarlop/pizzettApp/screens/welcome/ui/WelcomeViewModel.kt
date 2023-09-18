package es.fjmarlop.pizzettApp.screens.welcome.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.retrofit.models.NewProductoModel
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.screens.welcome.domain.WelcomeDomainService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(private val utils: Utils, private val welcomeDomainService: WelcomeDomainService): ViewModel() {

    private var productsList: List<NewProductoModel> = emptyList()

    fun goToLoginScreen(navHostController: NavHostController) {
        viewModelScope.launch(Dispatchers.Main){
            navHostController.popBackStack()
            utils.navigateToLogin(navHostController)
        }
    }

    init {
        viewModelScope.launch() {
           withContext(Dispatchers.IO){
               productsList = welcomeDomainService.getAllProductos()
               welcomeDomainService.insertRoomProducts(productsList)
           }
        }

    }
}