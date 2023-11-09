package es.fjmarlop.pizzettApp.dataBase.Remote.responses

import com.google.gson.annotations.SerializedName
import es.fjmarlop.pizzettApp.dataBase.Remote.models.CategoriaModel

data class CategoriaResponse(

    @SerializedName("id_categoria") val id_categoria: Int,
    @SerializedName("nombre_categoria") val nombre_categoria: String

)
{
    fun toCategoriaModel(): CategoriaModel {
        return CategoriaModel(id_categoria = id_categoria, nombre_categoria = nombre_categoria)
    }

}