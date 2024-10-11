package com.cevdetkilickeser.neyicez.domain

import com.google.firebase.auth.FirebaseAuth

interface AuthService {

    val auth: FirebaseAuth
}