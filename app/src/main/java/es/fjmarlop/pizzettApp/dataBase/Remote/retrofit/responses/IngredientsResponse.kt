package es.fjmarlop.pizzettApp.dataBase.Remote.retrofit.responses

import com.google.gson.annotations.SerializedName

data class IngredientsResponse(

    @SerializedName("ingredientName") val ingredientName: String,
    @SerializedName("id")val id: Int
)
