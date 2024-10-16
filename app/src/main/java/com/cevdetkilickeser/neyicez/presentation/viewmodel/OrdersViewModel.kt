package com.cevdetkilickeser.neyicez.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cevdetkilickeser.neyicez.data.model.Order
import com.cevdetkilickeser.neyicez.data.repo.CartRepository
import com.cevdetkilickeser.neyicez.utils.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(var crepo: CartRepository) : ViewModel(){

    val kullanici_adi = UserInfo.currentUser!!
    val orderList = MutableLiveData<List<Order>>()

    init {
        loadOrders(kullanici_adi)
    }

    fun loadOrders(kullanici_adi:String){
        CoroutineScope(Dispatchers.Main).launch{
            orderList.value = crepo.loadOrders(kullanici_adi)
        }

    }
}