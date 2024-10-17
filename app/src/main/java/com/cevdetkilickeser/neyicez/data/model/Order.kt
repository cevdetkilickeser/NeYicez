package com.cevdetkilickeser.neyicez.data.model

data class Order (
    val id: String? = null,
    val username: String = "",
    val orderDate: String = "",
    val cartList: List<Cart> = emptyList(),
    val orderTotal: String = ""
)