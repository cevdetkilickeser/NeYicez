package com.cevdetkilickeser.neyicez.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cevdetkilickeser.neyicez.R
import com.cevdetkilickeser.neyicez.databinding.FragmentSignUpBinding
import com.cevdetkilickeser.neyicez.presentation.viewmodel.SignupViewModel
import com.cevdetkilickeser.neyicez.utils.navigate
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignupViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        initObservers()
        initListeners()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel: SignupViewModel by viewModels()
        viewModel = tempViewModel
    }

    private fun initObservers() {
        viewModel.isSignupButtonEnabled.observe(viewLifecycleOwner) { isEnable ->
            binding.buttonSignup.isEnabled = isEnable
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT).show()
        }

        viewModel.isSignup.observe(viewLifecycleOwner) { isSignup ->
            handleLoginState(isSignup)
        }
    }

    private fun initListeners() {
        binding.emailSignup.addTextChangedListener {
            viewModel.onEmailChanged(it.toString())
        }

        binding.passwordSignup.addTextChangedListener {
            viewModel.onPasswordChanged(it.toString())
        }

        binding.buttonSignup.setOnClickListener {
            val email = binding.emailSignup.text.toString()
            val password = binding.passwordSignup.text.toString()
            viewModel.signup(email, password)
        }

        binding.buttonGoLogin.setOnClickListener {
            navigate(it, R.id.signUpToLogIn)
        }
    }

    private fun handleLoginState(isSignup: Boolean) {
        if (isSignup) {
            navigate(binding.root, R.id.signUpToUnconfirmed)
        }
    }
}