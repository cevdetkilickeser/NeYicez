package com.cevdetkilickeser.neyicez.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Food(
    @SerializedName ("yemek_id") val foodId: Int,
    @SerializedName ("yemek_adi") val foodName: String,
    @SerializedName ("yemek_resim_adi") val foodImageName: String,
    @SerializedName ("yemek_fiyat") val foodPrice: Int
) : Serializable