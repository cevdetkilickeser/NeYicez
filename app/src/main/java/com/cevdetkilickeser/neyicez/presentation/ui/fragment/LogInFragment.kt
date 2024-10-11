package com.cevdetkilickeser.neyicez.presentation.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cevdetkilickeser.neyicez.R
import com.cevdetkilickeser.neyicez.databinding.FragmentLogInBinding
import com.cevdetkilickeser.neyicez.presentation.ui.activity.MainActivity
import com.cevdetkilickeser.neyicez.presentation.viewmodel.LoginViewModel
import com.cevdetkilickeser.neyicez.utils.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogInBinding.inflate(inflater, container, false)

        initObservers()
        initListeners()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel: LoginViewModel by viewModels()
        viewModel = tempViewModel
    }

    private fun initObservers() {
        viewModel.isLoginButtonEnabled.observe(viewLifecycleOwner) { isEnable ->
            binding.buttonLogin.isEnabled = isEnable
        }

        viewModel.isLogin.observe(viewLifecycleOwner) { isLogin ->
            handleLoginState(isLogin)
        }
    }

    private fun initListeners() {
        binding.emailLogin.addTextChangedListener {
            viewModel.onEmailChanged(it.toString())
        }

        binding.passwordLogin.addTextChangedListener {
            viewModel.onPasswordChanged(it.toString())
        }

        binding.buttonLogin.setOnClickListener {
            val email = binding.emailLogin.text.toString()
            val password = binding.passwordLogin.text.toString()
            viewModel.login(email, password)
        }

        binding.buttonCreateAccount.setOnClickListener {
            navigate(it, R.id.logInToSignUp)
        }
    }

    private fun handleLoginState(isLogin: Boolean) {
        if (isLogin) {
            if (viewModel.isEmailVerified.value == true) {
                navigateToMainActivity()
            } else {
                navigate(binding.root, R.id.logInToUnconfirmed)
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}