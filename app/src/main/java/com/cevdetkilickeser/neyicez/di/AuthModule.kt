package com.cevdetkilickeser.neyicez.di

import com.cevdetkilickeser.neyicez.domain.AuthService
import com.cevdetkilickeser.neyicez.domain.AuthServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    abstract fun bindAuthService(authServiceImpl: AuthServiceImpl): AuthService

}