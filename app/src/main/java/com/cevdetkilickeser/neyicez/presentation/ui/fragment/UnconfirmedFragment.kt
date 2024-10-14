package com.cevdetkilickeser.neyicez.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.cevdetkilickeser.neyicez.R
import com.cevdetkilickeser.neyicez.databinding.FragmentUnconfirmedBinding
import com.cevdetkilickeser.neyicez.domain.AuthService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UnconfirmedFragment : Fragment() {

    private lateinit var binding: FragmentUnconfirmedBinding
    @Inject
    lateinit var authService: AuthService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUnconfirmedBinding.inflate(inflater, container, false)

        binding.buttonLogoutUnc.setOnClickListener {
            onClickButtonLogout(it)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                isEnabled = false
                requireActivity().finish()
            }

        })

        return binding.root
    }

    private fun onClickButtonLogout(view: View){
        authService.auth.signOut()
        Navigation.findNavController(view).navigate(R.id.unconfirmedToLogIn)
    }


}