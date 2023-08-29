package es.fjmarlop.pizzettApp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import es.fjmarlop.pizzettApp.models.AddressModel

@Entity(tableName = "address_table")
data class AddressEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "city") val city: String?,
    @ColumnInfo(name = "codPostal") val codPostal: String?,

    )

fun AddressEntity.toAddressModel() = AddressModel(name ?: "", address, city ?: "", codPostal ?: "")
