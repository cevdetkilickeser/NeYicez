package com.cevdetkilickeser.neyicez.data.repo

import com.cevdetkilickeser.neyicez.data.datasource.FoodsDataSource
import com.cevdetkilickeser.neyicez.data.model.Fav
import com.cevdetkilickeser.neyicez.domain.FirebaseDBService
import javax.inject.Inject

class FavRepository @Inject constructor (private val db: FirebaseDBService, private val datasource: FoodsDataSource){

    fun getFavs(username: String, onResult: (List<Fav>?) -> Unit) {
        db.firebaseDB.collection("fav_table")
            .whereEqualTo("username", username)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    onResult(null)
                } else {
                    val favList = mutableListOf<Fav>()
                    for (document in documents) {
                        val fav = document.toObject(Fav::class.java)
                        favList.add(fav)
                    }
                    onResult(favList)
                }
            }
            .addOnFailureListener {
                onResult(null)
            }
    }
}