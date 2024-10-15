package com.cevdetkilickeser.neyicez.data.repo

import com.cevdetkilickeser.neyicez.data.datasource.FoodsDataSource
import com.cevdetkilickeser.neyicez.data.model.Cart
import com.cevdetkilickeser.neyicez.data.model.Food

class CartRepository (private var dataSource: FoodsDataSource){

    suspend fun loadCart(userName: String) : List<Cart> = dataSource.loadCart(userName)

    suspend fun deleteFromCart(cartFoodId:Int, userName: String) = dataSource.deleteFromCart(cartFoodId,userName)


    suspend fun approveOrder(order:List<Cart>) = dataSource.approveOrder(order)

    suspend fun loadOrders(userName: String) = dataSource.loadOrders(userName)

    suspend fun addToCart(food: Food, foodOrderQuantity: Int, userName: String) {
        var existingItem: Cart? = null
        try {
            val cartFoods = loadCart(userName)
            existingItem = cartFoods.find { it.foodName == food.foodName }
        } catch (_: Exception) {}

        existingItem?.let {
            addToCartExistFood(food, foodOrderQuantity, existingItem, userName)
        } ?: run {
            addToCartNewFood(food, foodOrderQuantity, userName)
        }
    }

    private suspend fun addToCartExistFood(food: Food, foodOrderQuantity: Int, existingItem: Cart, userName: String) {
        val newQuantity = existingItem.foodOrderQuantity + foodOrderQuantity
        deleteFromCart(existingItem.cartFoodId, userName)
        dataSource.addToCard(
            food.foodName,
            food.foodImageName,
            food.foodPrice,
            newQuantity,
            userName
        )
    }

    private suspend fun addToCartNewFood(food: Food, foodOrderQuantity: Int, userName: String) {
        dataSource.addToCard(
            food.foodName,
            food.foodImageName,
            food.foodPrice,
            foodOrderQuantity,
            userName
        )
    }

}
