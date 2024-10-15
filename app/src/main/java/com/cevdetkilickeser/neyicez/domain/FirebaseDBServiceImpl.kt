package com.cevdetkilickeser.neyicez.domain

import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirebaseDBServiceImpl @Inject constructor() : FirebaseDBService {
    override val firebaseDB: FirebaseFirestore = FirebaseFirestore.getInstance()

}