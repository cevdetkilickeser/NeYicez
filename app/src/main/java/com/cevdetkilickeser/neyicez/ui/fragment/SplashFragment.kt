package com.cevdetkilickeser.neyicez.ui.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.cevdetkilickeser.neyicez.MainActivity
import com.cevdetkilickeser.neyicez.R
import com.cevdetkilickeser.neyicez.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var appPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        appPref = requireActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val introStatus = appPref.getBoolean("introStatus", false)
        val currentUser = auth.currentUser
        val isEmailVerified = currentUser?.isEmailVerified ?: false

        if (!introStatus) {
            navigate(R.id.splashToIntro)
        } else {
            if (currentUser == null) {
                navigate(R.id.splashToLogIn)
            } else {
                if (!isEmailVerified) {
                    navigate(R.id.splashToUnconfirmed)
                } else {
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }

        return binding.root

    }

    private fun navigate(id: Int) {
        Handler(Looper.getMainLooper()).postDelayed({
            Navigation.findNavController(binding.root).navigate(id)
        }, 1000)
    }
}