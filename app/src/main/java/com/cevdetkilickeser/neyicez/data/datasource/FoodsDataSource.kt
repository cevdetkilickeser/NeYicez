package com.cevdetkilickeser.neyicez.data.datasource

import com.cevdetkilickeser.neyicez.data.model.CRUDAnswer
import com.cevdetkilickeser.neyicez.data.model.Cart
import com.cevdetkilickeser.neyicez.data.model.Foods
import com.cevdetkilickeser.neyicez.data.model.Orders
import com.cevdetkilickeser.neyicez.retrofit.ApiService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FoodsDataSource(var fdao:ApiService) {

    val db = FirebaseFirestore.getInstance()

    suspend fun loadFoods() : List<Foods> =
        withContext(Dispatchers.IO){
            return@withContext fdao.loadFoods().yemekler
        }

    suspend fun loadCart(kullanici_adi: String) : List<Cart> =
        withContext(Dispatchers.IO){
            return@withContext fdao.loadCart(kullanici_adi).sepet_yemekler
        }

    suspend fun addToCard(yemek_adi:String,
                          yemek_resim_adi:String,
                          yemek_fiyat:Int,
                          yemek_siparis_adet:Int,
                          kullanici_adi:String) : CRUDAnswer = fdao.addToCart(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)

    suspend fun deleteFromCart(sepet_yemek_id:Int, kullanici_adi: String) : CRUDAnswer = fdao.deleteFromCart(sepet_yemek_id, kullanici_adi)

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