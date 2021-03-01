package com.example.crossingschedule.feature.auth.di

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.auth.data.mapper.TokenResponseToEitherMapper
import com.example.crossingschedule.feature.auth.data.repository.AuthProvider
import com.example.crossingschedule.feature.auth.data.repository.AuthProviderImpl
import com.example.crossingschedule.feature.auth.data.repository.LoginApiService
import com.example.crossingschedule.feature.auth.data.repository.AuthRepositoryImpl
import com.example.crossingschedule.feature.auth.domain.repository.AuthRepository
import com.example.crossingschedule.feature.auth.presentation.mapper.FailureToLoginErrorMapper
import com.example.crossingschedule.feature.auth.presentation.model.LoginError
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object LoginModule {

    @Provides
    fun provideLoginRepository(
        loginRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository = loginRepositoryImpl

    @Provides
    fun provideLoginErrorMapper(
        failureToLoginErrorMapper: FailureToLoginErrorMapper
    ): Mapper<LoginError, Failure> = failureToLoginErrorMapper

    @Provides
    fun provideAuthApi(
        authApiImpl: AuthProviderImpl
    ): AuthProvider = authApiImpl

    @Provides
    fun provideLoginApiService(
        retrofit: Retrofit
    ): LoginApiService = retrofit.create(LoginApiService::class.java)

    @Provides
    fun provideTokenResponseToEitherMapper(
        tokenResponseToEitherMapper: TokenResponseToEitherMapper
    ): Mapper<Either<Failure, Unit>, String> = tokenResponseToEitherMapper

}