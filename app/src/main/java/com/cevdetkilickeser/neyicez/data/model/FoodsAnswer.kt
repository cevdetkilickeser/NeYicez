package com.cevdetkilickeser.neyicez.data.model

import com.google.gson.annotations.SerializedName

data class FoodsAnswer(
    val success: Int,
    @SerializedName ("yemekler") val foods: List<Food>)