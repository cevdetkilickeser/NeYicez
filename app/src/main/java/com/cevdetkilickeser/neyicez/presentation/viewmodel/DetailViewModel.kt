package com.cevdetkilickeser.neyicez.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cevdetkilickeser.neyicez.data.model.Food
import com.cevdetkilickeser.neyicez.data.repo.CartRepository
import com.cevdetkilickeser.neyicez.data.repo.FavRepository
import com.cevdetkilickeser.neyicez.domain.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    authService: AuthService,
    private var cartRepo: CartRepository,
    private var favRepo: FavRepository
) : ViewModel() {

    private var username = authService.auth.currentUser?.email.toString()

    private var _qty = MutableLiveData(1)
    var qty: LiveData<Int> = _qty

    private var _isFav = MutableLiveData<Boolean>()
    var isFav: LiveData<Boolean> = _isFav

    private var _isLoading = MutableLiveData<Boolean>()
    var isLoading: LiveData<Boolean> = _isLoading

    fun favCheck(food: Food) {
        viewModelScope.launch {
            delay(300)
            favRepo.favCheck(username, food.foodId) { isFav ->
                _isFav.value = isFav
                _isLoading.value = false
            }
        }
    }

    fun addToFavs(food: Food){
        viewModelScope.launch {
            favRepo.addToFavs(food, username)
            _isFav.value = true
        }
    }

    fun deleteFromFavs(food: Food) {
        viewModelScope.launch {
            favRepo.deleteFromFavs(food, username)
            _isFav.value = false
        }
    }

    fun increaseQty() {
        if (_qty.value!! < 10) _qty.value = _qty.value!! + 1
    }

    fun decreaseQty() {
        if (_qty.value!! > 1) _qty.value = _qty.value!! - 1
    }

    fun addToCart(food: Food, foodOrderQuantity: Int) {
        viewModelScope.launch {
            cartRepo.addToCart(food, foodOrderQuantity, username)
        }
    }
}
