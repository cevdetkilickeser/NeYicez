package com.cevdetkilickeser.neyicez.di

import com.cevdetkilickeser.neyicez.domain.RepositoryService
import com.cevdetkilickeser.neyicez.domain.RepositoryServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)

abstract class RepositoryModule {

    @Binds
    abstract fun bindRepositoryService(repositoryServiceImpl: RepositoryServiceImpl): RepositoryService

}