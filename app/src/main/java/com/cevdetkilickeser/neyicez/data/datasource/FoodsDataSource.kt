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

class FoodsDataSource(private var apiService:ApiService) {

    val db = FirebaseFirestore.getInstance()

    suspend fun loadFoods() : List<Food> =
        withContext(Dispatchers.IO){
            return@withContext apiService.loadFoods().foods
        }

    suspend fun loadCart(userName: String) : List<Cart> =
        withContext(Dispatchers.IO){
            return@withContext apiService.loadCart(userName).cartFoods
        }

    suspend fun addToCard(foodName:String,
                          foodImageName:String,
                          foodPrice:Int,
                          foodOrderQuantity:Int,
                          userName:String) : CRUDAnswer = apiService.addToCart(foodName,foodImageName,foodPrice,foodOrderQuantity,userName)

    suspend fun deleteFromCart(cartFoodId:Int, userName: String) : CRUDAnswer = apiService.deleteFromCart(cartFoodId, userName)

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

    suspend fun loadOrders(userName: String): List<Orders> {
        val querySnapshot = db.collection("orders")
            .whereEqualTo("kullanici_adi", userName)
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