package com.example.crossingschedule.feature.login.di

import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.login.data.repository.LoginRepositoryImpl
import com.example.crossingschedule.feature.login.domain.repository.LoginRepository
import com.example.crossingschedule.feature.login.presentation.mapper.FailureToLoginErrorMapper
import com.example.crossingschedule.feature.login.presentation.model.LoginError
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object LoginModule {

    @Provides
    fun provideLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl
    ): LoginRepository = loginRepositoryImpl

    @Provides
    fun provideLoginErrorMapper(
        failureToLoginErrorMapper: FailureToLoginErrorMapper
    ): Mapper<LoginError, Failure> = failureToLoginErrorMapper

}