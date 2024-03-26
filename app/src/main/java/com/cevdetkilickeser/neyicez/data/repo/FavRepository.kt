package com.cevdetkilickeser.neyicez.data.repo

import com.cevdetkilickeser.neyicez.data.datasource.FoodsDataSource
import com.cevdetkilickeser.neyicez.data.entity.Cart

class FavRepository  (var fds: FoodsDataSource){

    suspend fun addToFav (yemek_adi:String,
                          yemek_resim_adi:String,
                          yemek_fiyat:Int,
                          yemek_fav_adet:Int,
                          kullanici_fav:String) = fds.addToFav(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_fav_adet,kullanici_fav)

    suspend fun loadFav(kullanici_fav: String) : List<Cart> = fds.loadFav(kullanici_fav)

    suspend fun deleteFromFav(fav_yemek_id:Int,kullanici_fav: String) = fds.deleteFromFav(fav_yemek_id,kullanici_fav)
}