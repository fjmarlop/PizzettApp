package es.fjmarlop.pizzettApp.core.retrofit.responses

import com.google.gson.annotations.SerializedName

data class TamaniosResponse(

    @SerializedName("id") val id: Int,
    @SerializedName("tamano") val tamano: String,
    @SerializedName("pvp") val pvp: Double
)
