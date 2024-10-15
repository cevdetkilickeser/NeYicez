package com.cevdetkilickeser.neyicez.di

import com.cevdetkilickeser.neyicez.domain.FirebaseDBService
import com.cevdetkilickeser.neyicez.domain.FirebaseDBServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class DatabaseModule {

    @Binds
    abstract fun bindFirebaseDBService(firebaseDBServiceImpl: FirebaseDBServiceImpl): FirebaseDBService
}