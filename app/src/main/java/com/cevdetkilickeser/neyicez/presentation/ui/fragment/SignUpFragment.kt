package com.cevdetkilickeser.neyicez.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.cevdetkilickeser.neyicez.R
import com.cevdetkilickeser.neyicez.databinding.FragmentSignUpBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        binding.buttonSignup.setOnClickListener {
            onClickButtonSignup(it)
        }

        binding.buttonGoLogin.setOnClickListener {
            onClickGoLogin(it)
        }


        return binding.root
    }

    private fun onClickButtonSignup(view: View){
        val email = binding.emailSignup.text.toString()
        val password = binding.passwordSignup.text.toString()

        if (checkSignupFields(email,password)){
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        sendEmailVerification(view)
                    }
                }
                .addOnFailureListener { exception ->
                    exception.localizedMessage?.let {
                        Snackbar.make(view,it,Snackbar.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun onClickGoLogin(view: View){
        Navigation.findNavController(view).navigate(R.id.signUpToLogIn)
    }

    private fun checkSignupFields(email:String, password: String) : Boolean {
        binding.textInputLayoutEmailSignup.error = null
        binding.textInputLayoutPasswordSignup.error = null

        if (email.isEmpty()){
            binding.textInputLayoutEmailSignup.error = getText(R.string.required_field)
            return false
        }

        if (password.isEmpty()){
            binding.textInputLayoutPasswordSignup.error = getText(R.string.required_field)
            return false
        }
        return true
    }

    private fun sendEmailVerification(view: View){
        auth.currentUser!!.sendEmailVerification()
            .addOnCompleteListener {
                Snackbar.make(view,getText(R.string.signup_success),Snackbar.LENGTH_LONG).show()
                Navigation.findNavController(view).navigate(R.id.signUpToUnconfirmed)
            }
            .addOnFailureListener { exception ->
                exception.localizedMessage?.let {
                    Snackbar.make(view, it,Snackbar.LENGTH_LONG).show()
                }
            }
    }


}