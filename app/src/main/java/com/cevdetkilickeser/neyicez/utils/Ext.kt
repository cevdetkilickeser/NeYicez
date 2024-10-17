package com.cevdetkilickeser.neyicez.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

fun navigate(view: View, action: Int) {
    Navigation.findNavController(view).navigate(action)
}

fun navigate(view: View, action: NavDirections) {
    Navigation.findNavController(view).navigate(action)
}

fun onClickBackButton(fragment: Fragment) {
    findNavController(fragment).popBackStack()
}

fun getCurrentDateTime(timestamp: Timestamp): String{
    val dateFormat = SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault())
    val date = timestamp.toDate()
    val currentDateTime = dateFormat.format(date)
    return currentDateTime
}