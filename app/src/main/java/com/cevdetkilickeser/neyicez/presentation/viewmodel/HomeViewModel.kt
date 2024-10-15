package com.cevdetkilickeser.neyicez.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cevdetkilickeser.neyicez.data.model.Cart
import com.cevdetkilickeser.neyicez.data.model.Food
import com.cevdetkilickeser.neyicez.data.repo.CartRepository
import com.cevdetkilickeser.neyicez.data.repo.FoodsRepository
import com.cevdetkilickeser.neyicez.domain.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val foodRepo: FoodsRepository,
    private val cartRepo: CartRepository,
    authService: AuthService
) : ViewModel() {

    private var userName = authService.auth.currentUser!!.email.toString()
    private val foodList = MutableLiveData<List<Food>>()
    val filteredFoodList = MutableLiveData<List<Food>>()

    init {
        loadFoods()
    }

    private fun loadFoods() {
        viewModelScope.launch {
            foodList.value = foodRepo.loadFoods()
            filteredFoodList.value = foodList.value
        }
    }

    fun addToCart(foodName: String, footPicture: String, foodPrice: Int) {
        var existingItem: Cart? = null
        viewModelScope.launch {
            try {
                val cartFoods = cartRepo.loadCart(userName) as ArrayList<Cart>
                existingItem = cartFoods.find { it.yemek_adi == foodName }
            } catch (_: Exception) {
            }
            if (existingItem != null) {
                val newQuantity = existingItem!!.yemek_siparis_adet + 1
                cartRepo.deleteFromCart(existingItem!!.sepet_yemek_id, userName)
                cartRepo.addToCart(foodName, footPicture, foodPrice, newQuantity, userName)
            } else {
                cartRepo.addToCart(foodName, footPicture, foodPrice, 1, userName)
            }
        }
    }

    fun searchFoods(query: String) {
        filteredFoodList.value =
            foodList.value?.filter { it.yemek_adi.contains(query, ignoreCase = true) }
                ?: listOf()
    }
}
