package com.cevdetkilickeser.neyicez.utils

import android.view.View
import androidx.navigation.Navigation

fun navigate(view: View, action: Int) {
    Navigation.findNavController(view).navigate(action)
}