package com.cevdetkilickeser.neyicez.di

import com.cevdetkilickeser.neyicez.data.datasource.FoodsDataSource
import com.cevdetkilickeser.neyicez.data.repo.CartRepository
import com.cevdetkilickeser.neyicez.data.repo.FavRepository
import com.cevdetkilickeser.neyicez.data.repo.FoodsRepository
import com.cevdetkilickeser.neyicez.retrofit.ApiUtils
import com.cevdetkilickeser.neyicez.retrofit.FoodsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFoodsRepository(fds:FoodsDataSource) : FoodsRepository {
        return FoodsRepository(fds)
    }

    @Provides
    @Singleton
    fun provideCartRepository(fds:FoodsDataSource) : CartRepository {
        return CartRepository(fds)
    }

    @Provides
    @Singleton
    fun provideFavRepository(fds:FoodsDataSource) : FavRepository {
        return FavRepository(fds)
    }

    @Provides
    @Singleton
    fun provideFoodsDatasource(fdao:FoodsDao) : FoodsDataSource {
        return FoodsDataSource(fdao)
    }

    @Provides
    @Singleton
    fun provideFoodsDao() : FoodsDao {
        return ApiUtils.getFoodsDao()
    }
}