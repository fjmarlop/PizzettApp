package es.fjmarlop.pizzettApp.screens.compra.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.roomDB.models.AddressModel
import es.fjmarlop.pizzettApp.core.roomDB.models.UserModel
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.screens.compra.domain.CompraDomainService
import es.fjmarlop.pizzettApp.screens.detailsAccount.domain.DetailsProfileDomainService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CompraViewModel @Inject constructor(
    private val utils: Utils,
    private val domainService: DetailsProfileDomainService,
    private val compraDomainService: CompraDomainService
) : ViewModel() {

    fun onClickTipoPedido(selected: Boolean) {
        _tipoPedido.value = !selected
    }

    fun goToDetalles(navHostController: NavHostController) {
        utils.navigateToDetailsProfile(navHostController)
    }

    fun msg(msg: String) {
        utils.mensajeToast(msg)
    }


    private val _tipoPedido = MutableStateFlow(false)
    val tipoPedido: StateFlow<Boolean> = _tipoPedido

    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel> = _user

    private val _listAddress = MutableLiveData<List<AddressModel>>()
    val listAddress: LiveData<List<AddressModel>> = _listAddress
    init {
        val email = _user.value?.email
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _user.postValue(domainService.getUser())
                _listAddress.postValue(email?.let { compraDomainService.getListAddres(it) })
            }
        }
    }
}