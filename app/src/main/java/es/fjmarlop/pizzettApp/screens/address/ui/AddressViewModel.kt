package es.fjmarlop.pizzettApp.screens.address.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.utils.Utils
import es.fjmarlop.pizzettApp.models.AddressModel
import es.fjmarlop.pizzettApp.screens.address.domain.AddressDomainService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val utils: Utils,
    private val addressDomainService: AddressDomainService
) : ViewModel() {
    fun goBack(navHostController: NavHostController) {
        utils.navigateToProfile(navHostController)
    }

    fun addAddress(addressModel: AddressModel) {
        viewModelScope.launch(Dispatchers.IO) {
            addressDomainService.addAddress(addressModel)
        }
    }

    fun getAddressList(email: String) {
        viewModelScope.launch() {
            val list = addressDomainService.getAddress(email)
            list.collect { items ->
                _addressList.emit(items)
            }
        }
    }

    fun onTextFieldsChanged(name: String, address: String, city: String, codPostal: String) {
        viewModelScope.launch {
            _addressName.emit(name)
            _addressAddress.emit(address)
            _addressCity.emit(city)
            _addressCodPostal.emit(codPostal)
            _isSaveEnable.emit(isTextFieldOK())

        }
    }

    private fun isTextFieldOK(): Boolean {
        return _addressAddress.value.isNotBlank()
                && _addressAddress.value.isNotBlank()
                && _addressCity.value.isNotBlank()
                && _addressCodPostal.value.isNotBlank()
    }

    fun close(navHostController: NavHostController) {
        utils.navigateToAddress(navHostController)
    }

    fun recuperarDatos(id: Int, name: String, address: String, city: String, codPostal: String) {
        viewModelScope.launch {
            _addressId.emit(id)
            _addressName.emit(name)
            _addressAddress.emit(address)
            _addressCity.emit(city)
            _addressCodPostal.emit(codPostal)
        }
    }

    fun reset() {
        viewModelScope.launch {
            //_addressId.emit(0)
            _addressName.emit("")
            _addressAddress.emit("")
            _addressCity.emit("")
            _addressCodPostal.emit("")
            _isSaveEnable.emit(false)
        }
    }

    fun updateAddress(
        id: Int, name: String, address: String, city: String, codPostal: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            addressDomainService.updateAddress(id, name, address, city, codPostal)
        }
    }

    fun deleteAddress(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            addressDomainService.deleteAddress(id)
        }
    }

    private val _addressName = MutableStateFlow("")
    val addressName: StateFlow<String> = _addressName

    private val _addressAddress = MutableStateFlow("")
    val addressAddress: StateFlow<String> = _addressAddress

    private val _addressCity = MutableStateFlow("")
    val addressCity: StateFlow<String> = _addressCity

    private val _addressCodPostal = MutableStateFlow("")
    val addressCodPostal: StateFlow<String> = _addressCodPostal

    private val _isSaveEnable = MutableStateFlow(false)
    val isSaveEnable: StateFlow<Boolean> = _isSaveEnable

    private val _addressList = MutableStateFlow<List<AddressModel>>(emptyList())
    val addressList: StateFlow<List<AddressModel>> = _addressList

    private val _addressId = MutableStateFlow(0)
    val addressId: StateFlow<Int> = _addressId

}
