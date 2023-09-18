package es.fjmarlop.pizzettApp.core.roomDB.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "producto_table")
data class ProductEntity(

    @PrimaryKey
    @ColumnInfo(name = "id_producto") val id_producto: Int,
    @ColumnInfo(name = "nombre_producto") val nombre_producto: String,
    @ColumnInfo(name = "imagen_producto") val imagen_producto: String,
    @ColumnInfo(name = "descripcion") val descripcion: String,
    @ColumnInfo(name = "categoria") val categoria: String,
    @ColumnInfo(name = "ingredients") val ingredients: List<String>,
    @ColumnInfo(name = "tamanios") val tamanios: List<TamaniosEntity>

)


class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

}

class TamaniosListConverter {
    @TypeConverter
    fun fromString(value: String): List<TamaniosEntity> {
        val listType = object : TypeToken<List<TamaniosEntity>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<TamaniosEntity>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}