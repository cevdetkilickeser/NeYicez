package com.cevdetkilickeser.neyicez.data.datasource

import com.cevdetkilickeser.neyicez.data.entity.CRUDAnswer
import com.cevdetkilickeser.neyicez.data.entity.Cart
import com.cevdetkilickeser.neyicez.data.entity.Foods
import com.cevdetkilickeser.neyicez.retrofit.FoodsDao
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FoodsDataSource(var fdao:FoodsDao) {

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

    suspend fun loadFav(kullanici_fav: String) : List<Cart> =
        withContext(Dispatchers.IO){
            return@withContext fdao.loadFav(kullanici_fav).sepet_yemekler
        }

    suspend fun addToFav (yemek_adi:String,
                          yemek_resim_adi:String,
                          yemek_fiyat:Int,
                          yemek_fav_adet:Int,
                          kullanici_fav:String) : CRUDAnswer = fdao.addToFav(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_fav_adet,kullanici_fav)

    suspend fun deleteFromFav(sepet_fav_id:Int, kullanici_fav: String) : CRUDAnswer = fdao.deleteFromFav(sepet_fav_id, kullanici_fav)

    suspend fun approveOrder(order: List<Cart>) {
        val data = hashMapOf(
            "order" to order)

        db.collection("orders")
            .add(data)
            .await()
    }
}