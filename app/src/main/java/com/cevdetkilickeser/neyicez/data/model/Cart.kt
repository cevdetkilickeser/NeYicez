package com.cevdetkilickeser.neyicez.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Cart (
    @SerializedName ("sepet_yemek_id") val cartFoodId: Int = 0,
    @SerializedName ("yemek_adi") val foodName: String = "",
    @SerializedName ("yemek_resim_adi") val foodImageName: String = "",
    @SerializedName ("yemek_fiyat") val foodPrice: Int = 0,
    @SerializedName ("yemek_siparis_adet") val foodOrderQuantity: Int = 0,
    @SerializedName ("kullanici_adi") val username: String = ""
) : Serializable