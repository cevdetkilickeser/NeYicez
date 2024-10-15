package com.cevdetkilickeser.neyicez.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cevdetkilickeser.neyicez.data.model.Cart
import com.cevdetkilickeser.neyicez.data.model.Food
import com.cevdetkilickeser.neyicez.data.repo.CartRepository
import com.cevdetkilickeser.neyicez.domain.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    authService: AuthService,
    private var cartRepo: CartRepository
) : ViewModel() {

    private var userName = authService.auth.currentUser?.email.toString()

    private var _qty = MutableLiveData(1)
    var qty: LiveData<Int> = _qty

    fun increaseQty() {
        if (_qty.value!! < 10) _qty.value = _qty.value!! + 1
    }

    fun decreaseQty() {
        if (_qty.value!! > 1) _qty.value = _qty.value!! - 1
    }

    fun addToCart(food: Food, yemek_siparis_adet: Int) {
        var existingItem: Cart? = null
        viewModelScope.launch {
            try {
                val cartFoods = cartRepo.loadCart(userName)
                existingItem = cartFoods.find { it.yemek_adi == food.yemek_adi }
            } catch (_: Exception) {
            }

            existingItem?.let {
                addToCartNew(food, yemek_siparis_adet, existingItem!!)
            } ?: run {
                addToCartExist(food, yemek_siparis_adet)
            }
        }
    }

    private suspend fun addToCartNew(food: Food, yemek_siparis_adet: Int, existingItem: Cart) {
        val newQuantity = existingItem.yemek_siparis_adet + yemek_siparis_adet
        cartRepo.deleteFromCart(existingItem.sepet_yemek_id, userName)
        cartRepo.addToCart(
            food.yemek_adi,
            food.yemek_resim_adi,
            food.yemek_fiyat,
            newQuantity,
            userName
        )
    }

    private suspend fun addToCartExist(food: Food, yemek_siparis_adet: Int) {
        cartRepo.addToCart(
            food.yemek_adi,
            food.yemek_resim_adi,
            food.yemek_fiyat,
            yemek_siparis_adet,
            userName
        )
    }
}
