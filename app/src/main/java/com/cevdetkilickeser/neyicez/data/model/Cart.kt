package com.cevdetkilickeser.neyicez.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Cart (
    @SerializedName ("sepet_yemek_id") val cartFoodId: Int,
    @SerializedName ("yemek_adi") val foodName: String,
    @SerializedName ("yemek_resim_adi") val foodImageName: String,
    @SerializedName ("yemek_fiyat") val foodPrice: Int,
    @SerializedName ("yemek_siparis_adet") val foodOrderQuantity: Int,
    @SerializedName ("kullanici_adi") val username: String
) : Serializable