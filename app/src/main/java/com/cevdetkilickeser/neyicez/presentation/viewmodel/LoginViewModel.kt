package com.cevdetkilickeser.neyicez.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cevdetkilickeser.neyicez.domain.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authService: AuthService) : ViewModel() {

    private val _email = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()

    private val _isLoginButtonEnabled = MediatorLiveData<Boolean>().apply {
        addSource(_email) { value = shouldEnableButton() }
        addSource(_password) { value = shouldEnableButton() }
    }
    val isLoginButtonEnabled: LiveData<Boolean> = _isLoginButtonEnabled

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> = _isLogin

    private var _isEmailVerified = MutableLiveData<Boolean>()
    val isEmailVerified: LiveData<Boolean> = _isEmailVerified

    init {
        _isLoginButtonEnabled.value = false
    }

    fun onEmailChanged(email: String) {
        _email.value = email
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
    }

    private fun shouldEnableButton(): Boolean {
        return !_email.value.isNullOrEmpty() && !_password.value.isNullOrEmpty()
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authService.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        checkEmailVerified()
                        _isLogin.value = true
                    }
                }.addOnFailureListener {
                _isLogin.value = false
            }
        }
    }

    private fun checkEmailVerified() {
        _isEmailVerified.value = authService.auth.currentUser?.isEmailVerified ?: false
    }

}