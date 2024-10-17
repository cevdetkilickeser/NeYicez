package com.cevdetkilickeser.neyicez.data.model

import com.google.firebase.Timestamp

data class Order (
    val id: String? = null,
    val username: String = "",
    val cartList: List<Cart> = emptyList(),
    val orderTotal: String = "",
    val orderDate: Timestamp = Timestamp.now()
)