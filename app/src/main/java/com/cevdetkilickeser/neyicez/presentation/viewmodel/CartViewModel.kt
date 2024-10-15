package com.cevdetkilickeser.neyicez.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cevdetkilickeser.neyicez.data.model.Cart
import com.cevdetkilickeser.neyicez.data.repo.CartRepository
import com.cevdetkilickeser.neyicez.domain.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    authService: AuthService,
    private val cartRepo: CartRepository
) : ViewModel() {

    private var username = authService.auth.currentUser?.email.toString()
    var cartList = MutableLiveData<List<Cart>>()
    var totalPrice = MutableLiveData("₺ 0")

    init {
        loadCart()
    }

    fun loadCart() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                cartList.value = cartRepo.loadCart(username)
                calculateTotalPrice(cartList.value!!)
            } catch (e: Exception) {
                totalPrice.value = "₺ 0"
            }
        }
    }

    fun deleteFromCart(cartFoodId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            if (cartList.value!!.size == 1) {
                cartRepo.deleteFromCart(cartFoodId, username)
                cartList.value = emptyList()
            } else {
                cartRepo.deleteFromCart(cartFoodId, username)
            }
            loadCart()
        }
    }

    private fun calculateTotalPrice(cartList: List<Cart>) {
        var totalPrc = 0
        CoroutineScope(Dispatchers.Main).launch {
            cartList.forEach {
                totalPrc += (it.foodOrderQuantity * it.foodPrice)
            }
            totalPrice.value = "₺ $totalPrc"
        }
    }

    fun approveOrder() {
        CoroutineScope(Dispatchers.Main).launch {
            val approveList = cartList.value
            val orderList = ArrayList<Cart>()
            if (!approveList.isNullOrEmpty()) {
                approveList.forEach {
                    orderList.add(it)
                    cartRepo.deleteFromCart(it.cartFoodId, username)
                }
                cartRepo.approveOrder(orderList)
                cartList.value = emptyList()
                totalPrice.value = "₺ 0"
            }
        }
    }


}