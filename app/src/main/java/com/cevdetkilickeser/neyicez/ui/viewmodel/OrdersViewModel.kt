package com.cevdetkilickeser.neyicez.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cevdetkilickeser.neyicez.data.entity.Cart
import com.cevdetkilickeser.neyicez.data.entity.Orders
import com.cevdetkilickeser.neyicez.data.repo.CartRepository
import com.cevdetkilickeser.neyicez.utils.UserInfo
import com.google.firestore.v1.StructuredQuery.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(var crepo: CartRepository) : ViewModel(){

    val kullanici_adi = UserInfo.currentUser!!
    val orderList = MutableLiveData<List<Orders>>()

    init {
        loadOrders(kullanici_adi)
    }

    fun loadOrders(kullanici_adi:String){
        CoroutineScope(Dispatchers.Main).launch{
            orderList.value = crepo.loadOrders(kullanici_adi)
        }

    }
}