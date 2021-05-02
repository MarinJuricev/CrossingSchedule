package com.example.crossingschedule.feature.auth.di

import com.example.crossingschedule.core.model.CrossingResponse
import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.auth.data.mapper.AuthResponseToEitherMapper
import com.example.crossingschedule.feature.auth.data.model.AuthResponse
import com.example.crossingschedule.feature.auth.data.service.AuthApiService
import com.example.crossingschedule.feature.auth.data.service.AuthProvider
import com.example.crossingschedule.feature.auth.data.service.AuthProviderImpl
import com.example.crossingschedule.feature.auth.data.repository.AuthRepositoryImpl
import com.example.crossingschedule.feature.auth.domain.model.AuthFailure
import com.example.crossingschedule.feature.auth.domain.repository.AuthRepository
import com.example.crossingschedule.feature.auth.presentation.mapper.FailureToLoginErrorMapper
import com.example.crossingschedule.feature.auth.presentation.mapper.FailureToSignUpErrorMapper
import com.example.crossingschedule.feature.auth.presentation.model.LoginError
import com.example.crossingschedule.feature.auth.presentation.model.SignUpError
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object AuthModule {

    @Provides
    fun provideAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository = authRepositoryImpl

    @Provides
    fun provideLoginErrorMapper(
        failureToLoginErrorMapper: FailureToLoginErrorMapper
    ): Mapper<LoginError, AuthFailure> = failureToLoginErrorMapper

    @Provides
    fun provideSignUpErrorMapper(
        failureToSignUpErrorMapper: FailureToSignUpErrorMapper
    ): Mapper<SignUpError, AuthFailure> = failureToSignUpErrorMapper

    @Provides
    fun provideAuthApi(
        authApiImpl: AuthProviderImpl
    ): AuthProvider = authApiImpl

    @Provides
    fun provideLoginApiService(
        retrofit: Retrofit
    ): AuthApiService = retrofit.create(AuthApiService::class.java)

    @Provides
    fun provideTokenResponseToEitherMapper(
        authResponseToEitherMapper: AuthResponseToEitherMapper
    ): Mapper<Either<AuthFailure, Unit>, CrossingResponse<AuthResponse>> =
        authResponseToEitherMapper

}