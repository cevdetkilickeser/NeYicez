package com.cevdetkilickeser.neyicez.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cevdetkilickeser.neyicez.data.model.Cart
import com.cevdetkilickeser.neyicez.data.repo.CartRepository
import com.cevdetkilickeser.neyicez.data.repo.FavRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavsViewModel @Inject constructor(var cartRepo:CartRepository, var favrepo: FavRepository) : ViewModel() {

    var kullanici_adi = com.cevdetkilickeser.neyicez.utils.UserInfo.currentUser!!
    var fkullanici_adi = "f$kullanici_adi"
    var favList = MutableLiveData<List<Cart>>()

    init {
        loadFavFoods(fkullanici_adi)
        if (favList.value!=null){
            Log.e("şş", favList.value!!.size.toString())
        }else{
            Log.e("şş", "size boş")}
    }

    fun loadFavFoods(kullanici_fav:String){
        CoroutineScope(Dispatchers.Main).launch {
            try {
//                favList.value = favrepo.loadFav(kullanici_fav)
            }catch (_:Exception){}
        }
    }
}