package es.fjmarlop.pizzettApp.vistas.cliente.detailsAccount.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.navigation.Navegadores
import es.fjmarlop.pizzettApp.dataBase.local.models.UserModel
import es.fjmarlop.pizzettApp.vistas.cliente.detailsAccount.domain.DetailsProfileDomainService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para la visualización y gestión de detalles del perfil de usuario.
 *
 * Este ViewModel interactúa con el servicio del dominio [DetailsProfileDomainService] para obtener
 * y actualizar información relacionada con el perfil de usuario, además de manejar la navegación en la aplicación.
 *
 * @property navegadores Instancia de [Navegadores] utilizada para la navegación en la aplicación.
 * @property domainService Instancia de [DetailsProfileDomainService] utilizado para acceder a la lógica de dominio del perfil.
 */
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


    /**
     * Obtine la información del usuario.
     */
    fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = domainService.getUser()
            _user.postValue(user)
        }
    }

    /**
     * Reconoce los cambios en el campo nombre del usuario.
     */
    fun onTextNameChange(name: String) {
        viewModelScope.launch {
            _name.emit(name)
        }
    }

    /**
     * Actualiza el campo nombre del usuario.
     */
    fun onClickNameEdit() {
        viewModelScope.launch {
            _isNameEditable.emit(!_isNameEditable.value)
        }
    }

    /**
     * Actualiza el campo teléfono del usuario.
     */
    fun onClickPhoneEdit() {
        viewModelScope.launch {
            _isPhoneEditable.emit(!_isPhoneEditable.value)
        }
    }

    /**
     * Vuelve hacia atrás
     */
    fun goBack(navHostController: NavHostController) {
        navegadores.navigateToProfile(navHostController)
    }

    /**
     * Reconoce los cambios en el campo teléfono del usuario.
     */
    fun onTextPhoneChange(phone: String) {
        viewModelScope.launch {
            _phone.emit(phone)
        }
    }

    /**
     * Actualiza el nombre del usuario y navega de regreso a los detalles del perfil.
     *
     * @param name Nuevo nombre del usuario.
     * @param email Correo electrónico del usuario cuyo nombre se va a actualizar.
     * @param navHostController Instancia de [NavHostController] para gestionar la navegación.
     */
    fun updateUserName(name: String, email: String, navHostController: NavHostController) {
        viewModelScope.launch(Dispatchers.IO) {
            domainService.updateUserName(name, email)
        }
        onClickNameEdit()
        navegadores.navigateToDetailsProfile(navHostController)
    }

    /**
     * Actualiza el número de teléfono del usuario y navega de regreso a los detalles del perfil.
     *
     * @param phone Nuevo número de teléfono del usuario.
     * @param email Correo electrónico del usuario cuyo número de teléfono se va a actualizar.
     * @param navHostController Instancia de [NavHostController] para gestionar la navegación.
     */
    fun updatePhone(phone: String, email: String, navHostController: NavHostController) {
        viewModelScope.launch(Dispatchers.IO) {
            domainService.updatePhone(phone, email)
        }
        onClickPhoneEdit()
        navegadores.navigateToDetailsProfile(navHostController)
    }

    /**
     * Navega hacia la pantalla de privacidad.
     * @param navHostController Controlador de navegación de Jetpack Compose.
     */
    fun gotoPrivacy(navHostController: NavHostController) {
        navegadores.navigateToPrivacyPolices(navHostController)
    }

    /**
     * Navega hacia la pantalla de terminos de uso.
     * @param navHostController Controlador de navegación de Jetpack Compose.
     */
    fun gotoTerms(navHostController: NavHostController) {
        navegadores.navigateToTermsOfUses(navHostController)
    }
}