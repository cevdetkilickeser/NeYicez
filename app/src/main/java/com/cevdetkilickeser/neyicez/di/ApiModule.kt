package com.cevdetkilickeser.neyicez.di

import com.cevdetkilickeser.neyicez.retrofit.ApiService
import com.cevdetkilickeser.neyicez.retrofit.ApiUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return ApiUtils.apiService()
    }
}