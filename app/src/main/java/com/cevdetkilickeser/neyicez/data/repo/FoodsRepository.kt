package com.cevdetkilickeser.neyicez.data.repo

import com.cevdetkilickeser.neyicez.data.datasource.FoodsDataSource
import com.cevdetkilickeser.neyicez.data.model.Food
import javax.inject.Inject

class FoodsRepository @Inject constructor(private var dataSource:FoodsDataSource) {

    suspend fun loadFoods() : List<Food> = dataSource.loadFoods()

}