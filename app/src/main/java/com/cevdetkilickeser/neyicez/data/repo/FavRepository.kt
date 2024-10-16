package com.cevdetkilickeser.neyicez.data.repo

import com.cevdetkilickeser.neyicez.data.datasource.FoodsDataSource
import com.cevdetkilickeser.neyicez.data.model.Fav
import com.cevdetkilickeser.neyicez.data.model.Food
import com.cevdetkilickeser.neyicez.domain.FirebaseDBService
import javax.inject.Inject

class FavRepository @Inject constructor(
    val db: FirebaseDBService,
    private val datasource: FoodsDataSource
) {

    inline fun getFavs(username: String, crossinline onResult: (List<Fav>?) -> Unit) {
        db.firebaseDB.collection("favCollection")
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

    fun favCheck(username: String, foodId: Int, onResult: (Boolean) -> Unit) {
        db.firebaseDB.collection("favCollection")
            .whereEqualTo("username", username)
            .whereEqualTo("foodId", foodId)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    onResult(false)
                } else {
                    onResult(true)
                }
            }
            .addOnFailureListener {
                onResult(false)
            }
    }

    fun addToFavs(food: Food, username: String) {
        val fav = Fav(username, food.foodId, food.foodName, food.foodPrice, food.foodImageName)
        db.firebaseDB.collection("favCollection")
            .add(fav)
            .addOnSuccessListener { }
            .addOnFailureListener { }
    }

    fun deleteFromFavs(food: Food, username: String) {
        val favCollection = db.firebaseDB.collection("favCollection")

        favCollection
            .whereEqualTo("username", username)
            .whereEqualTo("foodId", food.foodId)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    favCollection.document(documents.first().id)
                        .delete()
                }
            }
    }
}