package com.cevdetkilickeser.neyicez.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cevdetkilickeser.neyicez.data.model.Fav
import com.cevdetkilickeser.neyicez.data.repo.CartRepository
import com.cevdetkilickeser.neyicez.data.repo.FavRepository
import com.cevdetkilickeser.neyicez.domain.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavsViewModel @Inject constructor(
    authService: AuthService,
    var cartRepo: CartRepository,
    private var favrepo: FavRepository
) : ViewModel() {

    private var username = authService.auth.currentUser?.email.toString()

    private val _favList = MutableLiveData<List<Fav>>()
    val favList: LiveData<List<Fav>> = _favList

    init {
        loadFavs()
    }

    private fun loadFavs() {
        favrepo.getFavs(username) {
            _favList.value = it ?: emptyList()
        }
    }
}