package es.fjmarlop.pizzettApp.core.retrofit.responses

import com.google.gson.annotations.SerializedName
import es.fjmarlop.pizzettApp.core.retrofit.models.CategoriaModel

data class CategoriaResponse(

    @SerializedName("id_categoria") val id_categoria: Int,
    @SerializedName("nombre_categoria") val nombre_categoria: String

)
{
    fun toCategoriaModel():CategoriaModel{
        return CategoriaModel(id_categoria = id_categoria, nombre_categoria = nombre_categoria)
    }

}