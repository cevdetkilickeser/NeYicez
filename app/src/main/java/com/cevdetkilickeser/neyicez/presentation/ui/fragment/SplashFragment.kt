package com.cevdetkilickeser.neyicez.presentation.ui.fragment

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
import com.cevdetkilickeser.neyicez.presentation.ui.activity.MainActivity
import com.cevdetkilickeser.neyicez.R
import com.cevdetkilickeser.neyicez.databinding.FragmentSplashBinding
import com.cevdetkilickeser.neyicez.domain.AuthService
import com.cevdetkilickeser.neyicez.utils.navigate
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class SplashFragment: Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private lateinit var appPref: SharedPreferences
    @Inject
    lateinit var authService: AuthService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        appPref = requireActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        Handler(Looper.getMainLooper()).postDelayed({
            navigateToNext()
        }, 1000)

        return binding.root
    }

    private fun navigateToNext() {
        val introStatus = appPref.getBoolean("introStatus", false)
        val currentUser = authService.auth.currentUser
        val isEmailVerified = currentUser?.isEmailVerified ?: false

        when {
            !introStatus -> navigate(binding.root, R.id.splashToIntro)
            currentUser == null -> navigate(binding.root, R.id.splashToLogIn)
            !isEmailVerified -> navigate(binding.root, R.id.splashToUnconfirmed)
            else -> navigateToMainActivity()
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}