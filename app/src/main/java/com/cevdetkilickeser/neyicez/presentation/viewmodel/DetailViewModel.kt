package com.cevdetkilickeser.neyicez.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        viewModelScope.launch {
            cartRepo.addToCart(food, yemek_siparis_adet, userName)
        }
    }
}
