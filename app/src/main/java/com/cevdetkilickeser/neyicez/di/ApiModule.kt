package com.cevdetkilickeser.neyicez.di

import com.cevdetkilickeser.neyicez.retrofit.ApiService
import com.cevdetkilickeser.neyicez.retrofit.ApiUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return ApiUtils.apiService()
    }
}