package com.cevdetkilickeser.neyicez.data.repo

import com.cevdetkilickeser.neyicez.data.datasource.FoodsDataSource
import com.cevdetkilickeser.neyicez.data.model.Cart
import com.cevdetkilickeser.neyicez.data.model.Food
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartRepository (var fds: FoodsDataSource){

    suspend fun loadCart(kullanici_adi: String) : List<Cart> = fds.loadCart(kullanici_adi)

    suspend fun deleteFromCart(sepet_yemek_id:Int,kullanici_adi: String) = fds.deleteFromCart(sepet_yemek_id,kullanici_adi)


    suspend fun approveOrder(order:List<Cart>) = fds.approveOrder(order)

    suspend fun loadOrders(kullanici_adi: String) = fds.loadOrders(kullanici_adi)

    suspend fun addToCart(food: Food, yemek_siparis_adet: Int, userName: String) {
        var existingItem: Cart? = null
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cartFoods = loadCart(userName)
                existingItem = cartFoods.find { it.yemek_adi == food.yemek_adi }
            } catch (_: Exception) {}

            existingItem?.let {
                addToCartExistFood(food, yemek_siparis_adet, userName)
            } ?: run {
                addToCartNewFood(food, yemek_siparis_adet, existingItem!!, userName)
            }
        }
    }

    private suspend fun addToCartNewFood(food: Food, yemek_siparis_adet: Int, existingItem: Cart, userName: String) {
        val newQuantity = existingItem.yemek_siparis_adet + yemek_siparis_adet
        deleteFromCart(existingItem.sepet_yemek_id, userName)
        fds.addToCard(
            food.yemek_adi,
            food.yemek_resim_adi,
            food.yemek_fiyat,
            newQuantity,
            userName
        )
    }

    private suspend fun addToCartExistFood(food: Food, yemek_siparis_adet: Int, userName: String) {
        fds.addToCard(
            food.yemek_adi,
            food.yemek_resim_adi,
            food.yemek_fiyat,
            yemek_siparis_adet,
            userName
        )
    }

}
