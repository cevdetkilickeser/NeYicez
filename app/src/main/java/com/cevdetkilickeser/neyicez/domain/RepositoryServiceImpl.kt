package com.cevdetkilickeser.neyicez.domain

import com.cevdetkilickeser.neyicez.data.datasource.FoodsDataSource
import com.cevdetkilickeser.neyicez.data.repo.CartRepository
import com.cevdetkilickeser.neyicez.data.repo.FavRepository
import com.cevdetkilickeser.neyicez.data.repo.FoodsRepository

class RepositoryServiceImpl(
    override val foodsRepository: FoodsRepository,
    override val favRepository: FavRepository,
    override val cartRepository: CartRepository,
    override val dataSource: FoodsDataSource
) : RepositoryService {

}