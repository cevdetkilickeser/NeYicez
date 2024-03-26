package com.cevdetkilickeser.neyicez.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.cevdetkilickeser.neyicez.MainActivity
import com.cevdetkilickeser.neyicez.R
import com.cevdetkilickeser.neyicez.databinding.FragmentLogInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLogInBinding.inflate(inflater,container,false)
        auth = FirebaseAuth.getInstance()

        checkActiveUser()

        binding.buttonLogin.setOnClickListener {
            onClickButtonLogin(it)
        }

        binding.buttonCreateAccount.setOnClickListener {
            onClickButtonCreateAccount(it)
        }

        binding.buttonGoogleLogin.setOnClickListener {
            signIn()
        }

        return binding.root
    }

    private fun checkActiveUser(){
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun checkLoginFields(email:String, password: String) : Boolean {
        binding.textInputLayoutEmailLogin.error = null
        binding.textInputLayoutPasswordLogin.error = null

        if (email.isEmpty()){
            binding.textInputLayoutEmailLogin.error = getString(R.string.required_field)
            return false
        }
        if (password.isEmpty()){
            binding.textInputLayoutPasswordLogin.error = getString(R.string.required_field)
            binding.textInputLayoutPasswordLogin.errorIconDrawable = null
            return false
        }
        return true
    }

    private fun onClickButtonCreateAccount(view: View){
        Navigation.findNavController(view).navigate(R.id.logInToSignUp)
    }

    private fun onClickButtonLogin(view: View) {
        val email = binding.emailLogin.text.toString()
        val password = binding.passwordLogin.text.toString()

        if (checkLoginFields(email,password)){
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        if (auth.currentUser!!.isEmailVerified){
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        }else{
                            Navigation.findNavController(view).navigate(R.id.logInToUnconfirmed)
                        }
                    }
                }.addOnFailureListener{
                    Snackbar.make(view,getText(R.string.login_failed),Snackbar.LENGTH_SHORT).show()
                }
        }
    }

    private val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                Log.e("GoogleSignIn", "helal lan yaptın")
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.e("GoogleSignIn", "Google sign in failed")
            }
        }
    }

    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        val signInIntent = googleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.e("GoogleSignIn", "helal lan yaptın")
                    val user = auth.currentUser
                } else {
                    // Oturum açma başarısız oldu, hata mesajını kullanıcıya gösterin veya uygun şekilde işleyin.
                    Log.e("GoogleSignIn", "signInWithCredential:failure")
                }
            }
    }
}
