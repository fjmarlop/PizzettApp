package es.fjmarlop.pizzettApp.vistas.cliente.address.domain

import es.fjmarlop.pizzettApp.dataBase.local.models.AddressModel
import es.fjmarlop.pizzettApp.dataBase.local.models.toAddressEntity
import es.fjmarlop.pizzettApp.vistas.cliente.address.data.AddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Servicio de dominio que gestiona las operaciones relacionadas con la dirección del usuario.
 *
 * @property addressRepository Repositorio [AddressRepository] que proporciona métodos para acceder y manipular datos de dirección en la base de datos.
 */
class AddressDomainService @Inject constructor(private val addressRepository: AddressRepository) {

    /**
     * Obtiene la dirección asociada a un usuario mediante su dirección de correo electrónico.
     *
     * @param email Dirección de correo electrónico del usuario.
     * @return [Flow] que emite una lista de [AddressModel] representando la dirección del usuario.
     */
    fun getAddress(email: String): Flow<List<AddressModel>> {
        return addressRepository.getAddress(email).map { items ->
            items.map {
                AddressModel(
                    id = it.id,
                    name = it.name,
                    address = it.address,
                    city = it.city ?: "",
                    codPostal = it.codPostal ?: "",
                    email = email
                )
            }
        }
    }

    /**
     * Agrega una nueva dirección asociada a un usuario.
     *
     * @param addressModel Modelo [AddressModel] que representa la dirección a agregar.
     */
    suspend fun addAddress(addressModel: AddressModel) {
        addressRepository.addAddress(addressModel.toAddressEntity())
    }

    /**
     * Actualiza la información de una dirección existente asociada a un usuario.
     *
     * @param id Identificador único de la dirección.
     * @param name Nombre asociado a la dirección.
     * @param address Dirección física.
     * @param city Ciudad de residencia.
     * @param codPostal Código postal de la dirección.
     */
    suspend fun updateAddress(id: Int, name: String, address: String, city: String, codPostal: String) {
        addressRepository.updateAddress(id, name, address, city, codPostal)
    }

    /**
     * Elimina una dirección asociada a un usuario mediante su identificador único.
     *
     * @param id Identificador único de la dirección a eliminar.
     */
    suspend fun deleteAddress(id: Int) {
        addressRepository.deleteAddress(id)
    }
}
