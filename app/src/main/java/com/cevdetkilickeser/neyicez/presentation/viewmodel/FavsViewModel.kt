package com.cevdetkilickeser.neyicez.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cevdetkilickeser.neyicez.data.model.Fav
import com.cevdetkilickeser.neyicez.data.model.Food
import com.cevdetkilickeser.neyicez.data.repo.CartRepository
import com.cevdetkilickeser.neyicez.data.repo.FavRepository
import com.cevdetkilickeser.neyicez.domain.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavsViewModel @Inject constructor(
    authService: AuthService,
    var cartRepo: CartRepository,
    private var favRepo: FavRepository
) : ViewModel() {

    private var username = authService.auth.currentUser?.email.toString()

    private val _favList = MutableLiveData<List<Fav>>()
    val favList: LiveData<List<Fav>> = _favList

    private var _isLoading = MutableLiveData<Boolean>()
    var isLoading: LiveData<Boolean> = _isLoading

    fun loadFavs() {
        viewModelScope.launch {
            favRepo.getFavs(username) { favList ->
                _favList.value = favList ?: emptyList()
                _isLoading.value = false
            }
        }
    }

    fun addToCart(food: Food) {
        viewModelScope.launch {
            cartRepo.addToCart(food, 1, username)
        }
    }
}