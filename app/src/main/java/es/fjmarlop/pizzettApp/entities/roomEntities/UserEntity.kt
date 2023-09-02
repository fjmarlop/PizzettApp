package es.fjmarlop.pizzettApp.entities.roomEntities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import es.fjmarlop.pizzettApp.models.UserModel

@Entity(tableName = "user_table", indices = [Index(value = ["email"] ,unique = true)])
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "phone") val phone:String?

)

fun UserEntity.toUserModel(): UserModel {
    return UserModel(email ?: "", name ?: "", phone ?: "")
}








