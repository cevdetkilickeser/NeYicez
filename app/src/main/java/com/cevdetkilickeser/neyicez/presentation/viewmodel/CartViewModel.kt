package com.cevdetkilickeser.neyicez.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cevdetkilickeser.neyicez.data.model.Cart
import com.cevdetkilickeser.neyicez.data.repo.CartRepository
import com.cevdetkilickeser.neyicez.domain.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    authService: AuthService,
    private val cartRepo: CartRepository
) : ViewModel() {

    private var username = authService.auth.currentUser?.email.toString()

    private val _cartList = MutableLiveData<List<Cart>>()
    val cartList: LiveData<List<Cart>> = _cartList

    private val _orderTotal = MutableLiveData<String>()
    val orderTotal: LiveData<String> = _orderTotal

    fun loadCart() {
        viewModelScope.launch {
            try {
                _cartList.value = cartRepo.loadCart(username)
                calculateOrderTotal(_cartList.value!!)
            } catch (e: Exception) {
                _orderTotal.value = "₺ 0"
            }
        }
    }

    fun deleteFromCart(cart: Cart) {
        viewModelScope.launch {
            if (_cartList.value!!.size == 1) {
                cartRepo.deleteFromCart(cart.cartFoodId, username)
                _cartList.value = emptyList()
            } else {
                cartRepo.deleteFromCart(cart.cartFoodId, username)
            }
            loadCart()
        }
    }

    private fun calculateOrderTotal(cartList: List<Cart>) {
        var totalPrc = 0
        viewModelScope.launch {
            cartList.forEach {
                totalPrc += (it.foodOrderQuantity * it.foodPrice)
            }
            _orderTotal.value = "₺ $totalPrc"
        }
    }

    fun approveOrder() {
        viewModelScope.launch {
            if (_cartList.value!!.isNotEmpty()) {
                cartRepo.approveOrder(_cartList.value!!, username)
                _cartList.value = emptyList()
                _orderTotal.value = "₺ 0"
            }
        }
    }

    fun calculateCartItemTotal(foodPrice: Int, foodOrderQuantity: Int): Int {
        return foodPrice * foodOrderQuantity
    }
}