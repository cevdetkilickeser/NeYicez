package com.cevdetkilickeser.neyicez.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cevdetkilickeser.neyicez.data.entity.Cart
import com.cevdetkilickeser.neyicez.data.entity.Foods
import com.cevdetkilickeser.neyicez.data.repo.CartRepository
import com.cevdetkilickeser.neyicez.data.repo.FavRepository
import com.cevdetkilickeser.neyicez.data.repo.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val frepo:FoodsRepository,
                                        val crepo:CartRepository) : ViewModel() {

    var kullanici_adi = com.cevdetkilickeser.neyicez.utils.UserInfo.currentUser!!
    val foodsList = MutableLiveData<List<Foods>>()
    val filteredFoodsList = MutableLiveData<List<Foods>>()

    init {
        loadFoods()
    }

    fun loadFoods(){
        CoroutineScope(Dispatchers.Main).launch {
            foodsList.value = frepo.loadFoods()
            filteredFoodsList.value = foodsList.value
        }
    }

    fun addToCart(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int){
        var existingItem: Cart? = null
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val cartFoods = crepo.loadCart(kullanici_adi) as ArrayList<Cart>
                existingItem = cartFoods.find { it.yemek_adi == yemek_adi }
            }catch (_:Exception){}
            if (existingItem != null) {
                val newQuantity = existingItem!!.yemek_siparis_adet + 1
                crepo.deleteFromCart(existingItem!!.sepet_yemek_id, kullanici_adi)
                crepo.addToCart(yemek_adi, yemek_resim_adi, yemek_fiyat, newQuantity, kullanici_adi)
            } else {
                crepo.addToCart(yemek_adi, yemek_resim_adi, yemek_fiyat, 1, kullanici_adi)
            }
        }
    }

    fun searchFoods(query: String) {
        filteredFoodsList.value = foodsList.value?.filter { it.yemek_adi.contains(query, ignoreCase = true) }
            ?: listOf()
    }
}
