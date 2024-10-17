package com.cevdetkilickeser.neyicez.data.datasource

import com.cevdetkilickeser.neyicez.data.model.CRUDAnswer
import com.cevdetkilickeser.neyicez.data.model.Cart
import com.cevdetkilickeser.neyicez.data.model.Food
import com.cevdetkilickeser.neyicez.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FoodsDataSource @Inject constructor(private var apiService: ApiService) {

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
}