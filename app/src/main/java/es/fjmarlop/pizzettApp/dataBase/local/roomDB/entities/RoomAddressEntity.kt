package es.fjmarlop.pizzettApp.dataBase.local.roomDB.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import es.fjmarlop.pizzettApp.dataBase.local.roomDB.models.AddressModel

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

fun AddressEntity.toAddressModel() = AddressModel(id, name, address, city ?: "", codPostal ?: "", email)
