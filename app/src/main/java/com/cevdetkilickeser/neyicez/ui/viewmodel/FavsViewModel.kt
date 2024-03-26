package com.cevdetkilickeser.neyicez.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cevdetkilickeser.neyicez.data.entity.Cart
import com.cevdetkilickeser.neyicez.data.repo.CartRepository
import com.cevdetkilickeser.neyicez.data.repo.FavRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavsViewModel @Inject constructor(var crepo:CartRepository, var favrepo: FavRepository) : ViewModel() {

    var kullanici_adi = com.cevdetkilickeser.neyicez.utils.UserInfo.currentUser!!
    var fkullanici_adi = "f$kullanici_adi"
    var favList = MutableLiveData<List<Cart>>()

    init {
        loadFavFoods(fkullanici_adi)
        if (favList.value!=null){
            Log.e("şş", favList.value!!.size.toString())
        }else{
            Log.e("şş", "size boş")}
    }

    fun loadFavFoods(kullanici_fav:String){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                favList.value = favrepo.loadFav(kullanici_fav)
            }catch (_:Exception){}
        }
    }

    fun addToCart(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int,yemek_siparis_adet:Int){
        var existingItem: Cart? = null
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val cartFoods = crepo.loadCart(kullanici_adi)
                existingItem = cartFoods.find { it.yemek_adi == yemek_adi }
            }catch (_:Exception){}
            if (existingItem != null) {
                val newQuantity = existingItem!!.yemek_siparis_adet + yemek_siparis_adet
                crepo.deleteFromCart(existingItem!!.sepet_yemek_id, kullanici_adi)
                crepo.addToCart(yemek_adi, yemek_resim_adi, yemek_fiyat, newQuantity, kullanici_adi)
            } else {
                crepo.addToCart(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, kullanici_adi)
            }
        }
    }
}