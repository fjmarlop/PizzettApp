package es.fjmarlop.pizzettApp.vistas.cliente.compra.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.navigation.Navegadores
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.dataBase.local.models.AddressModel
import es.fjmarlop.pizzettApp.dataBase.local.models.UserModel
import es.fjmarlop.pizzettApp.vistas.cliente.compra.domain.CompraDomainService
import es.fjmarlop.pizzettApp.vistas.cliente.detailsAccount.domain.DetailsProfileDomainService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CompraViewModel @Inject constructor(
    private val utils: Utils,
    private val navegadores: Navegadores,
    private val domainService: DetailsProfileDomainService,
    private val compraDomainService: CompraDomainService
) : ViewModel() {


    fun goToDetalles(navHostController: NavHostController) {
        navegadores.navigateToDetailsProfile(navHostController)
    }

    fun msg(msg: String) {
        utils.mensajeToast(msg)
    }


    private val _local = MutableStateFlow(false)
    val local: StateFlow<Boolean> = _local

    private val _domicilio = MutableStateFlow(true)
    val domicilio: StateFlow<Boolean> = _domicilio

    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel> = _user

    private val _listAddress = MutableLiveData<List<AddressModel>>()
    val listAddress: LiveData<List<AddressModel>> = _listAddress


    fun onChangleLocal() {
        _local.value = false
        _domicilio.value = true
    }

    fun onChangeDomicilio() {
        _domicilio.value = false
        _local.value = true
    }

    fun getUser() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _user.postValue(domainService.getUser())

            }
        }
    }

    fun getListAddress(email: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _listAddress.postValue(compraDomainService.getListAddres(email))
            }
        }
    }

}