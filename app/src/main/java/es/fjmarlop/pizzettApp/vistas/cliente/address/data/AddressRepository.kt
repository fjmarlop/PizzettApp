package es.fjmarlop.pizzettApp.vistas.cliente.address.data

import es.fjmarlop.pizzettApp.dataBase.local.dao.AddressDao
import es.fjmarlop.pizzettApp.dataBase.local.entities.AddressEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repositorio que maneja las operaciones relacionadas con la dirección del usuario.
 *
 * @property addressDao Instancia de [AddressDao] que proporciona métodos de acceso a la base de datos para la entidad de dirección.
 */
@Singleton
class AddressRepository @Inject constructor(private val addressDao: AddressDao) {

    /**
     * Obtiene la dirección asociada a un usuario mediante su dirección de correo electrónico.
     *
     * @param email Dirección de correo electrónico del usuario.
     * @return [Flow] que emite una lista de [AddressEntity] representando la dirección del usuario.
     */
    fun getAddress(email: String): Flow<List<AddressEntity>> {
        return addressDao.getAddress(email)
    }

    /**
     * Agrega una nueva dirección a la base de datos.
     *
     * @param addressEntity Entidad [AddressEntity] que representa la dirección a agregar.
     */
    suspend fun addAddress(addressEntity: AddressEntity) {
        addressDao.addAddress(addressEntity)
    }

    /**
     * Actualiza la información de una dirección existente en la base de datos.
     *
     * @param id Identificador único de la dirección.
     * @param name Nombre asociado a la dirección.
     * @param address Dirección física.
     * @param city Ciudad de residencia.
     * @param codPostal Código postal de la dirección.
     */
    suspend fun updateAddress(id: Int, name: String, address: String, city: String, codPostal: String) {
        addressDao.updateAddress(id, name, address, city, codPostal)
    }

    /**
     * Elimina una dirección de la base de datos mediante su identificador único.
     *
     * @param id Identificador único de la dirección a eliminar.
     */
    suspend fun deleteAddress(id: Int) {
        addressDao.deleteAddress(id)
    }
}
