package com.cevdetkilickeser.neyicez.data.model

import com.google.gson.annotations.SerializedName

data class CartAnswer(
    @SerializedName ("sepet_yemekler") val cartFoods: List<Cart>,
    val success: Int
)