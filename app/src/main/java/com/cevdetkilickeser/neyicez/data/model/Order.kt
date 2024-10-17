package com.cevdetkilickeser.neyicez.data.model

data class Order (
    val cartList: List<Cart>,
    val username: String
)