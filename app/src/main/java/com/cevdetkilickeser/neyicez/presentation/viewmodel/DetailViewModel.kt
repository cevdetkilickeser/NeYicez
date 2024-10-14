package com.cevdetkilickeser.neyicez.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.cevdetkilickeser.neyicez.data.model.Cart
import com.cevdetkilickeser.neyicez.data.repo.CartRepository
import com.cevdetkilickeser.neyicez.data.repo.FavRepository
import com.cevdetkilickeser.neyicez.domain.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    authService: AuthService,
    private var cartRepo: CartRepository,
    var favRepo: FavRepository
) : ViewModel() {

    private var userName = authService.auth.currentUser?.email.toString()

    fun addToCart(
        yemek_adi: String,
        yemek_resim_adi: String,
        yemek_fiyat: Int,
        yemek_siparis_adet: Int
    ) {
        var existingItem: Cart? = null
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val cartFoods = cartRepo.loadCart(userName)
                existingItem = cartFoods.find { it.yemek_adi == yemek_adi }
            } catch (_: Exception) {
            }
            if (existingItem != null) {
                val newQuantity = existingItem!!.yemek_siparis_adet + yemek_siparis_adet
                cartRepo.deleteFromCart(existingItem!!.sepet_yemek_id, userName)
                cartRepo.addToCart(yemek_adi, yemek_resim_adi, yemek_fiyat, newQuantity, userName)
            } else {
                cartRepo.addToCart(
                    yemek_adi,
                    yemek_resim_adi,
                    yemek_fiyat,
                    yemek_siparis_adet,
                    userName
                )
            }
        }
    }

    fun addToFav(
        yemek_adi: String,
        yemek_resim_adi: String,
        yemek_fiyat: Int,
        yemek_fav_adet: Int
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            //favrepo.addToFav(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_fav_adet,fkullanici_adi)
        }
    }

    fun deleteFromFav(yemek_adi: String) {
        CoroutineScope(Dispatchers.Main).launch {
//            val favList = favrepo.loadFav(fkullanici_adi)
//            val existingItem = favList.find { it.yemek_adi == yemek_adi }
//            favrepo.deleteFromFav(existingItem!!.sepet_yemek_id,fkullanici_adi)
        }
    }

    fun checkFav(yemek_adi: String): Boolean {
        var isFav = false
//        CoroutineScope(Dispatchers.Main).launch {
//            var existingItem:Cart?=null
//            try {
//                val favList = favrepo.loadFav(fkullanici_adi)
//                existingItem = favList.find { it.yemek_adi == yemek_adi }
//            }catch (_:Exception){
//                isFav = false
//            }
//            isFav = existingItem != null
//        }
        return isFav
    }
}
