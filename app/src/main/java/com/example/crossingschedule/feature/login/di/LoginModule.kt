package com.example.crossingschedule.feature.login.di

import com.example.crossingschedule.feature.login.data.repository.LoginRepositoryImpl
import com.example.crossingschedule.feature.login.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object LoginModule {

    @Provides
    fun provideLoginRepository(): LoginRepository = LoginRepositoryImpl()
}