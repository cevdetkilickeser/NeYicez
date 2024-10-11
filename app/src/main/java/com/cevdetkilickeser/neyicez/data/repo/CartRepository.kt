package com.cevdetkilickeser.neyicez.data.repo

import com.cevdetkilickeser.neyicez.data.datasource.FoodsDataSource
import com.cevdetkilickeser.neyicez.data.model.Cart

class CartRepository (var fds: FoodsDataSource){

    suspend fun addToCart(yemek_adi:String,
                          yemek_resim_adi:String,
                          yemek_fiyat:Int,
                          yemek_siparis_adet:Int,
                          kullanici_adi:String) = fds.addToCard(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)

    suspend fun loadCart(kullanici_adi: String) : List<Cart> = fds.loadCart(kullanici_adi)

    suspend fun deleteFromCart(sepet_yemek_id:Int,kullanici_adi: String) = fds.deleteFromCart(sepet_yemek_id,kullanici_adi)


    suspend fun approveOrder(order:List<Cart>) = fds.approveOrder(order)

    suspend fun loadOrders(kullanici_adi: String) = fds.loadOrders(kullanici_adi)
}
