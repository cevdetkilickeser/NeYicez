package com.cevdetkilickeser.neyicez.data.repo

import com.cevdetkilickeser.neyicez.data.datasource.FoodsDataSource
import com.cevdetkilickeser.neyicez.data.model.Cart
import com.cevdetkilickeser.neyicez.data.model.Food

class CartRepository (private var dataSource: FoodsDataSource){

    suspend fun loadCart(username: String) : List<Cart> = dataSource.loadCart(username)

    suspend fun deleteFromCart(cartFoodId:Int, username: String) = dataSource.deleteFromCart(cartFoodId,username)


    suspend fun approveOrder(order:List<Cart>) = dataSource.approveOrder(order)

    suspend fun loadOrders(username: String) = dataSource.loadOrders(username)

    suspend fun addToCart(food: Food, foodOrderQuantity: Int, username: String) {
        var existingItem: Cart? = null
        try {
            val cartFoods = loadCart(username)
            existingItem = cartFoods.find { it.foodName == food.foodName }
        } catch (_: Exception) {}

        existingItem?.let {
            addToCartExistFood(food, foodOrderQuantity, existingItem, username)
        } ?: run {
            addToCartNewFood(food, foodOrderQuantity, username)
        }
    }

    private suspend fun addToCartExistFood(food: Food, foodOrderQuantity: Int, existingItem: Cart, username: String) {
        val newQuantity = existingItem.foodOrderQuantity + foodOrderQuantity
        deleteFromCart(existingItem.cartFoodId, username)
        dataSource.addToCard(
            food.foodName,
            food.foodImageName,
            food.foodPrice,
            newQuantity,
            username
        )
    }

    private suspend fun addToCartNewFood(food: Food, foodOrderQuantity: Int, username: String) {
        dataSource.addToCard(
            food.foodName,
            food.foodImageName,
            food.foodPrice,
            foodOrderQuantity,
            username
        )
    }

}
