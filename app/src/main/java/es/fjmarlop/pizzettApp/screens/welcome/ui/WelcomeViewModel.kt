package es.fjmarlop.pizzettApp.screens.welcome.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(private val utils: Utils): ViewModel() {

    fun goToLoginScreen(navHostController: NavHostController) {
        viewModelScope.launch(Dispatchers.Main){
            navHostController.popBackStack()
            utils.navigateToLogin(navHostController)
        }
    }

}