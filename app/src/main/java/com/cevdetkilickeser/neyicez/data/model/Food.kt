package com.cevdetkilickeser.neyicez.data.model

import java.io.Serializable

data class Food(val yemek_id: Int,
                val yemek_adi: String,
                val yemek_fiyat: Int,
                val yemek_resim_adi: String) : Serializable