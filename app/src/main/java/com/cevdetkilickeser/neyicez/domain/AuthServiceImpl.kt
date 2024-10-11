package com.cevdetkilickeser.neyicez.domain

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthServiceImpl @Inject constructor() : AuthService {
    override val auth: FirebaseAuth = FirebaseAuth.getInstance()
}