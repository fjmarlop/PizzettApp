package es.fjmarlop.pizzettApp.vistas.cliente.address.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettApp.core.navigation.Navegadores
import es.fjmarlop.pizzettApp.dataBase.local.models.AddressModel
import es.fjmarlop.pizzettApp.vistas.cliente.address.domain.AddressDomainService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel de la pantalla de gestión de direcciones.
 *
 * @property navegadores Instancia de [Navegadores] utilizada para navegar entre pantallas.
 * @property addressDomainService Servicio de dominio [AddressDomainService] que gestiona operaciones relacionadas con direcciones.
 */
@HiltViewModel
class AddressViewModel @Inject constructor(
    private val navegadores: Navegadores,
    private val addressDomainService: AddressDomainService
) : ViewModel() {

    /**
     * Navega hacia atrás en la interfaz de usuario utilizando el [NavHostController].
     *
     * @param navHostController [NavHostController] utilizado para la navegación.
     */
    fun goBack(navHostController: NavHostController) {
        navegadores.navigateToProfile(navHostController)
    }

    /**
     * Agrega una nueva dirección.
     *
     * @param addressModel Modelo [AddressModel] que representa la dirección a agregar.
     */
    fun addAddress(addressModel: AddressModel) {
        viewModelScope.launch(Dispatchers.IO) {
            addressDomainService.addAddress(addressModel)
        }
    }

    /**
     * Obtiene la lista de direcciones asociadas a un usuario mediante su dirección de correo electrónico.
     *
     * @param email Dirección de correo electrónico del usuario.
     */
    fun getAddressList(email: String) {
        viewModelScope.launch() {
            val list = addressDomainService.getAddress(email)
            list.collect { items ->
                _addressList.emit(items)
            }
        }
    }

    /**
     * Maneja los cambios en los campos de texto y actualiza el estado de guardado.
     *
     * @param name Nombre de la dirección.
     * @param address Dirección física.
     * @param city Ciudad de residencia.
     * @param codPostal Código postal de la dirección.
     */
    fun onTextFieldsChanged(name: String, address: String, city: String, codPostal: String) {
        viewModelScope.launch {
            _addressName.emit(name)
            _addressAddress.emit(address)
            _addressCity.emit(city)
            _addressCodPostal.emit(codPostal)
            _isSaveEnable.emit(isTextFieldOK())
        }
    }

    /**
     * Verifica si los campos de texto son válidos para activar el botón de guardado.
     *
     * @return `true` si los campos son válidos, `false` de lo contrario.
     */
    private fun isTextFieldOK(): Boolean {
        return _addressAddress.value.isNotBlank()
                && _addressCity.value.isNotBlank()
                && _addressCodPostal.value.isNotBlank()
    }

    /**
     * Cierra la pantalla de gestión de direcciones y navega a la pantalla de direcciones.
     *
     * @param navHostController [NavHostController] utilizado para la navegación.
     */
    fun close(navHostController: NavHostController) {
        navegadores.navigateToAddress(navHostController)
    }

    /**
     * Recupera los datos de una dirección para su edición.
     *
     * @param id Identificador único de la dirección.
     * @param name Nombre de la dirección.
     * @param address Dirección física.
     * @param city Ciudad de residencia.
     * @param codPostal Código postal de la dirección.
     */
    fun recuperarDatos(id: Int, name: String, address: String, city: String, codPostal: String) {
        viewModelScope.launch {
            _addressId.emit(id)
            _addressName.emit(name)
            _addressAddress.emit(address)
            _addressCity.emit(city)
            _addressCodPostal.emit(codPostal)
        }
    }

    /**
     * Restablece el estado de los campos de texto.
     */
    fun reset() {
        viewModelScope.launch {
            _addressName.emit("")
            _addressAddress.emit("")
            _addressCity.emit("")
            _addressCodPostal.emit("")
            _isSaveEnable.emit(false)
        }
    }

    /**
     * Actualiza la información de una dirección existente.
     *
     * @param id Identificador único de la dirección.
     * @param name Nombre de la dirección.
     * @param address Dirección física.
     * @param city Ciudad de residencia.
     * @param codPostal Código postal de la dirección.
     */
    fun updateAddress(id: Int, name: String, address: String, city: String, codPostal: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addressDomainService.updateAddress(id, name, address, city, codPostal)
        }
    }

    /**
     * Elimina una dirección existente.
     *
     * @param id Identificador único de la dirección.
     */
    fun deleteAddress(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            addressDomainService.deleteAddress(id)
        }
    }

    // Propiedades de flujo para observar en la interfaz de usuario

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
