package com.cevdetkilickeser.neyicez.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cevdetkilickeser.neyicez.data.model.Cart
import com.cevdetkilickeser.neyicez.data.model.Foods
import com.cevdetkilickeser.neyicez.data.repo.CartRepository
import com.cevdetkilickeser.neyicez.data.repo.FoodsRepository
import com.cevdetkilickeser.neyicez.domain.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val foodRepo: FoodsRepository,
    private val cartRepo: CartRepository,
    authService: AuthService
) : ViewModel() {

    private var userName = authService.auth.currentUser!!.email.toString()
    private val foodsList = MutableLiveData<List<Foods>>()
    val filteredFoodsList = MutableLiveData<List<Foods>>()

    init {
        loadFoods()
    }

    private fun loadFoods() {
        viewModelScope.launch {
            foodsList.value = foodRepo.loadFoods()
            filteredFoodsList.value = foodsList.value
        }
    }

    fun addToCart(foodName: String, footPicture: String, foodPrice: Int) {
        var existingItem: Cart? = null
        CoroutineScope(Dispatchers.Main).launch {
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
        filteredFoodsList.value =
            foodsList.value?.filter { it.yemek_adi.contains(query, ignoreCase = true) }
                ?: listOf()
    }
}
