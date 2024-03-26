package com.cevdetkilickeser.neyicez.data.entity

import java.io.Serializable

data class Foods(val yemek_id: Int,
                 val yemek_adi: String,
                 val yemek_fiyat: Int,
                 val yemek_resim_adi: String) : Serializable