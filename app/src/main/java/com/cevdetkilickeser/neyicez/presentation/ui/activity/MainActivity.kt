package com.cevdetkilickeser.neyicez.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.cevdetkilickeser.neyicez.R
import com.cevdetkilickeser.neyicez.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivSignOut.setOnClickListener {
            signOut()
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNav,navHostFragment.navController)
    }

    private fun signOut(){
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, MainActivityStart::class.java)
        startActivity(intent)
        finish()
    }
}