package es.fjmarlop.pizzettApp.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class UserEntity(

    @PrimaryKey
    val email: String

) {

}