package es.fjmarlop.pizzettApp.dataBase.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import es.fjmarlop.pizzettApp.dataBase.local.models.AddressModel

/**
 * Entidad de base de datos que representa una dirección.
 *
 * @property id Identificador único de la dirección (generado automáticamente).
 * @property name Nombre asociado a la dirección.
 * @property address Dirección física.
 * @property city Ciudad de la dirección (puede ser nulo).
 * @property codPostal Código postal de la dirección (puede ser nulo).
 * @property email Correo electrónico asociado a la dirección.
 */
@Entity(tableName = "address_table", indices = [Index(value = ["name"], unique = true)])
data class AddressEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "city") val city: String?,
    @ColumnInfo(name = "codPostal") val codPostal: String?,
    @ColumnInfo(name = "email") val email: String
)

/**
 * Extensión que convierte la entidad [AddressEntity] a un modelo [AddressModel].
 *
 * @return Objeto [AddressModel] creado a partir de la entidad [AddressEntity].
 */
fun AddressEntity.toAddressModel() = AddressModel(id, name, address, city ?: "", codPostal ?: "", email)

