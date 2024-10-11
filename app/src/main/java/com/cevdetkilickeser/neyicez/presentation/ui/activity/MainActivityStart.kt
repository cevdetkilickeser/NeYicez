package com.cevdetkilickeser.neyicez.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cevdetkilickeser.neyicez.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivityStart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_start)
    }
}