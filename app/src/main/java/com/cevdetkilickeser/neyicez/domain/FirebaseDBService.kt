package com.cevdetkilickeser.neyicez.domain

import com.google.firebase.firestore.FirebaseFirestore

interface FirebaseDBService {

    val firebaseDB: FirebaseFirestore
}