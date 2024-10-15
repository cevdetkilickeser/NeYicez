package com.cevdetkilickeser.neyicez.domain

import com.cevdetkilickeser.neyicez.data.datasource.FoodsDataSource
import com.cevdetkilickeser.neyicez.data.repo.CartRepository
import com.cevdetkilickeser.neyicez.data.repo.FavRepository
import com.cevdetkilickeser.neyicez.data.repo.FoodsRepository

interface RepositoryService {

    val foodsRepository: FoodsRepository
    val favRepository: FavRepository
    val cartRepository: CartRepository
    val dataSource: FoodsDataSource
}