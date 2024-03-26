package com.cevdetkilickeser.neyicez.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.cevdetkilickeser.neyicez.R
import com.cevdetkilickeser.neyicez.databinding.FragmentUnconfirmedBinding
import com.google.firebase.auth.FirebaseAuth

class UnconfirmedFragment : Fragment() {

    private lateinit var binding: FragmentUnconfirmedBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUnconfirmedBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        binding.buttonLogoutUnc.setOnClickListener {
            onClickButtonLogout(it)
        }

        return binding.root
    }

    private fun onClickButtonLogout(view: View){
        auth.signOut()
        Navigation.findNavController(view).navigate(R.id.unconfirmedToLogIn)
    }
}