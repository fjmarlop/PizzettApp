package es.fjmarlop.pizzettApp.vistas.cliente.detailsAccount.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.navigation.Navegadores
import es.fjmarlop.pizzettApp.dataBase.local.roomDB.models.UserModel
import es.fjmarlop.pizzettApp.vistas.cliente.detailsAccount.domain.DetailsProfileDomainService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailProfileViewModel @Inject constructor(
    private val navegadores: Navegadores,
    private val domainService: DetailsProfileDomainService
) :
    ViewModel() {


    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel> = _user


    private val _isNameEditable = MutableStateFlow(false)
    val isNameEditable: StateFlow<Boolean> = _isNameEditable.asStateFlow()

    private val _isPhoneEditable = MutableStateFlow(false)
    val isPhoneEditable: StateFlow<Boolean> = _isPhoneEditable.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone.asStateFlow()


    fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = domainService.getUser()
            _user.postValue(user)
        }
    }

    fun onTextNameChange(name: String) {
        viewModelScope.launch {
            _name.emit(name)
        }
    }


    fun onClickNameEdit() {
        viewModelScope.launch {
            _isNameEditable.emit(!_isNameEditable.value)
        }
    }

    fun onClickPhoneEdit() {
        viewModelScope.launch {
            _isPhoneEditable.emit(!_isPhoneEditable.value)
        }
    }

    fun goBack(navHostController: NavHostController) {
        navegadores.navigateToProfile(navHostController)
    }

    fun onTextPhoneChange(phone: String) {
        viewModelScope.launch {
            _phone.emit(phone)
        }
    }

    fun updateUserName(name: String, email: String, navHostController: NavHostController){
        viewModelScope.launch(Dispatchers.IO){
            domainService.updateUserName(name, email)
        }
        onClickNameEdit()
        navegadores.navigateToDetailsProfile(navHostController)
    }

    fun updatePhone(phone: String, email: String, navHostController: NavHostController){
        viewModelScope.launch(Dispatchers.IO){
            domainService.updatePhone(phone, email)
        }
        onClickPhoneEdit()
        navegadores.navigateToDetailsProfile(navHostController)
    }
}