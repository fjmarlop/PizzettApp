package es.fjmarlop.pizzettApp.dataBase.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import es.fjmarlop.pizzettApp.dataBase.local.models.UserModel

/**
 * Entidad de base de datos que representa un usuario.
 *
 * @property id Identificador único del usuario (generado automáticamente).
 * @property email Dirección de correo electrónico del usuario (única en la base de datos).
 * @property name Nombre del usuario (puede ser nulo).
 * @property phone Número de teléfono del usuario (puede ser nulo).
 */
@Entity(tableName = "user_table", indices = [Index(value = ["email"], unique = true)])
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "phone") val phone: String?
)

/**
 * Extensión que convierte la entidad [UserEntity] a un modelo [UserModel].
 *
 * @return Objeto [UserModel] creado a partir de la entidad [UserEntity].
 */
fun UserEntity.toUserModel(): UserModel {
    return UserModel(email, name ?: "", phone ?: "")
}








