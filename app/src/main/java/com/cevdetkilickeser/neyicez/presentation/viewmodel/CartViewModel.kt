package com.cevdetkilickeser.neyicez.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cevdetkilickeser.neyicez.data.model.Cart
import com.cevdetkilickeser.neyicez.data.repo.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(var crepo : CartRepository) : ViewModel() {

    var kullanici_adi = com.cevdetkilickeser.neyicez.utils.UserInfo.currentUser!!
    var cartList = MutableLiveData<List<Cart>>()
    var totalPrice = MutableLiveData("₺ 0")

    init {
        loadCart()
    }

    fun loadCart(){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                cartList.value = crepo.loadCart(kullanici_adi)
                calculateTotalPrica(cartList.value!!)
            }catch (e:Exception) {
                totalPrice.value = "₺ 0"
            }
        }
    }

    fun deleteFromCart(yemek_sepet_id:Int){
        CoroutineScope(Dispatchers.Main).launch {
            if (cartList.value!!.size == 1){
                crepo.deleteFromCart(yemek_sepet_id,kullanici_adi)
                cartList.value = emptyList()
            }else{
                crepo.deleteFromCart(yemek_sepet_id,kullanici_adi)
            }
            loadCart()
        }
    }

    fun calculateTotalPrica(cartList:List<Cart>){
        var totalPrc = 0
        CoroutineScope(Dispatchers.Main).launch {
            cartList.forEach {
                totalPrc += (it.yemek_siparis_adet * it.yemek_fiyat)
            }
            totalPrice.value = "₺ $totalPrc"
        }
    }

    fun approveOrder(){
            CoroutineScope(Dispatchers.Main).launch {
                val approveList = cartList.value
                val orderList = ArrayList<Cart>()
                if (approveList.isNullOrEmpty()){}
                else{
                    approveList.forEach {
                        orderList.add(it)
                        crepo.deleteFromCart(it.sepet_yemek_id,kullanici_adi)
                    }
                    crepo.approveOrder(orderList)
                    cartList.value = emptyList()
                    totalPrice.value = "₺ 0"
                }
        }
    }


}