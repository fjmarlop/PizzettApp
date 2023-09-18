package es.fjmarlop.pizzettApp.core.roomDB.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tamanios_table")
data class TamaniosEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")val id: Int,

    @ColumnInfo(name = "tamano") val tamano: String,
    @ColumnInfo(name = "pvp") val pvp: Double

)
