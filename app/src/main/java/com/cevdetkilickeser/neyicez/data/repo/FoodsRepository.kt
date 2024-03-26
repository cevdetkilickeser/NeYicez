package com.cevdetkilickeser.neyicez.data.repo

import com.cevdetkilickeser.neyicez.data.datasource.FoodsDataSource
import com.cevdetkilickeser.neyicez.data.entity.CRUDAnswer
import com.cevdetkilickeser.neyicez.data.entity.Cart
import com.cevdetkilickeser.neyicez.data.entity.Foods
import com.cevdetkilickeser.neyicez.retrofit.FoodsDao

class FoodsRepository(var fds:FoodsDataSource) {

    suspend fun loadFoods() : List<Foods> = fds.loadFoods()

}