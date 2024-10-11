package com.cevdetkilickeser.neyicez.data.repo

import com.cevdetkilickeser.neyicez.data.datasource.FoodsDataSource
import com.cevdetkilickeser.neyicez.data.model.Foods

class FoodsRepository(var fds:FoodsDataSource) {

    suspend fun loadFoods() : List<Foods> = fds.loadFoods()

}