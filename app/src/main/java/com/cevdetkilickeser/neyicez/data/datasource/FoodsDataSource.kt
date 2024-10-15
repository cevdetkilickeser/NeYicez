package com.cevdetkilickeser.neyicez.data.datasource

import com.cevdetkilickeser.neyicez.data.model.CRUDAnswer
import com.cevdetkilickeser.neyicez.data.model.Cart
import com.cevdetkilickeser.neyicez.data.model.Food
import com.cevdetkilickeser.neyicez.data.model.Orders
import com.cevdetkilickeser.neyicez.retrofit.ApiService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FoodsDataSource @Inject constructor(private var apiService: ApiService) {

    private val db = FirebaseFirestore.getInstance()

    suspend fun loadFoods(): List<Food> =
        withContext(Dispatchers.IO) {
            return@withContext apiService.loadFoods().foods
        }

    suspend fun loadCart(username: String): List<Cart> =
        withContext(Dispatchers.IO) {
            return@withContext apiService.loadCart(username).cartFoods
        }

    suspend fun addToCard(
        foodName: String,
        foodImageName: String,
        foodPrice: Int,
        foodOrderQuantity: Int,
        username: String
    ): CRUDAnswer =
        apiService.addToCart(foodName, foodImageName, foodPrice, foodOrderQuantity, username)

    suspend fun deleteFromCart(cartFoodId: Int, username: String): CRUDAnswer =
        apiService.deleteFromCart(cartFoodId, username)

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    suspend fun approveOrder(order: List<Cart>) {
        try {
            db.collection("orders")
                .add(mapOf("order" to order))
                .await()
        } catch (e: Exception) {
            // Hata durumunu ele al
        }
    }

    suspend fun loadOrders(username: String): List<Orders> {
        val querySnapshot = db.collection("orders")
            .whereEqualTo("kullanici_adi", username)
            .get()
            .await()

        val orders = mutableListOf<Orders>()
        for (document in querySnapshot.documents) {
            val order = document.toObject(Orders::class.java)
            if (order != null) {
                orders.add(order)
            }
        }
        return orders
    }
}