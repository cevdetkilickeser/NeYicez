package com.cevdetkilickeser.neyicez.data.repo

import com.cevdetkilickeser.neyicez.data.datasource.FoodsDataSource
import com.cevdetkilickeser.neyicez.data.model.Cart
import com.cevdetkilickeser.neyicez.data.model.Food
import com.cevdetkilickeser.neyicez.data.model.Order
import com.cevdetkilickeser.neyicez.domain.FirebaseDBService
import javax.inject.Inject

class CartRepository  @Inject constructor (val db: FirebaseDBService, private var dataSource: FoodsDataSource){

    suspend fun loadCart(username: String) : List<Cart> = dataSource.loadCart(username)

    suspend fun deleteFromCart(cartFoodId:Int, username: String) = dataSource.deleteFromCart(cartFoodId,username)

    suspend fun addToCart(food: Food, foodOrderQuantity: Int, username: String) {
        var existingCartItem: Cart? = null
        try {
            val cartFoods = loadCart(username)
            existingCartItem = cartFoods.find { it.foodName == food.foodName }
        } catch (_: Exception) {}

        existingCartItem?.let {
            addToCartExistItem(food, foodOrderQuantity, it, username)
        } ?: run {
            addToCartNewItem(food, foodOrderQuantity, username)
        }
    }

    private suspend fun addToCartExistItem(food: Food, foodOrderQuantity: Int, existingCartItem: Cart, username: String) {
        val newQuantity = existingCartItem.foodOrderQuantity + foodOrderQuantity
        deleteFromCart(existingCartItem.cartFoodId, username)
        dataSource.addToCard(
            food.foodName,
            food.foodImageName,
            food.foodPrice,
            newQuantity,
            username
        )
    }

    private suspend fun addToCartNewItem(food: Food, foodOrderQuantity: Int, username: String) {
        dataSource.addToCard(
            food.foodName,
            food.foodImageName,
            food.foodPrice,
            foodOrderQuantity,
            username
        )
    }

    suspend fun approveOrder(cartList: List<Cart>, username: String, orderTotal: String) {
        for (cart in cartList) {
            deleteFromCart(cart.cartFoodId, cart.username)
        }

        val orderDate = "22.10.2023 15:50"
        val order = Order(null, username, orderDate, cartList, orderTotal)
        db.firebaseDB.collection("orderCollection")
            .add(order)
            .addOnSuccessListener { }
            .addOnFailureListener { }
    }

    inline fun getOrders(username: String, crossinline onResult: (List<Order>?) -> Unit) {
        db.firebaseDB.collection("orderCollection")
            .whereEqualTo("username", username)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    onResult(null)
                } else {
                    val orderList = mutableListOf<Order>()
                    for (document in documents) {
                        val order = document.toObject(Order::class.java).copy(id = document.id)
                        orderList.add(order)
                    }
                    onResult(orderList)
                }
            }
            .addOnFailureListener {
                onResult(null)
            }
    }

}
