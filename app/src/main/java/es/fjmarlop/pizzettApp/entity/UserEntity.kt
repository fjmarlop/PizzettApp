package es.fjmarlop.pizzettApp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class UserEntity(

    @PrimaryKey
    val email: String

) {

}