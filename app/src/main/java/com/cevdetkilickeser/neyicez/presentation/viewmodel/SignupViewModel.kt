package com.cevdetkilickeser.neyicez.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cevdetkilickeser.neyicez.domain.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val authService: AuthService) : ViewModel() {

    private val _email = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()

    private val _isSignupButtonEnabled = MediatorLiveData(false).apply {
        addSource(_email) { value = shouldEnableButton() }
        addSource(_password) { value = shouldEnableButton() }
    }
    val isSignupButtonEnabled: LiveData<Boolean> = _isSignupButtonEnabled

    private val _isSignup = MutableLiveData<Boolean>()
    val isSignup: LiveData<Boolean> = _isSignup

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun onEmailChanged(email: String) {
        _email.value = email
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
    }

    private fun shouldEnableButton(): Boolean {
        return !_email.value.isNullOrEmpty() && !_password.value.isNullOrEmpty()
    }

    fun signup(email: String, password: String) {
        viewModelScope.launch {
            authService.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _isSignup.value = true
                        sendEmailVerification()
                    }
                }
                .addOnFailureListener { exception ->
                    _isSignup.value = false
                    _errorMessage.value = exception.localizedMessage?.toString()
                }
        }
    }

    private fun sendEmailVerification(){
        viewModelScope.launch {
            authService.auth.currentUser!!.sendEmailVerification()
                .addOnCompleteListener {
                }
                .addOnFailureListener {
                }
        }
    }
}