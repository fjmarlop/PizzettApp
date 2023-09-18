package es.fjmarlop.pizzettApp.core.retrofit.responses

import com.google.gson.annotations.SerializedName

data class IngredientsResponse(

    @SerializedName("ingredientName") val ingredientName: String,
    @SerializedName("id")val id: Int
)
