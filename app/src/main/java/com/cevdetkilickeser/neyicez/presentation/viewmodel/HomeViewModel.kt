package com.cevdetkilickeser.neyicez.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun addToCart(food: Food) {
        viewModelScope.launch {
            cartRepo.addToCart(food, 1, userName)
        }
    }

    fun searchFoods(query: String) {
        filteredFoodList.value =
            foodList.value?.filter { it.foodName.contains(query, ignoreCase = true) }
                ?: listOf()
    }
}
