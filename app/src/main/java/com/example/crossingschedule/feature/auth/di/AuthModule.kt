package com.example.crossingschedule.feature.auth.di

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.Failure
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.auth.data.mapper.AuthenticateUserResponseToEitherMapper
import com.example.crossingschedule.feature.auth.data.model.LoginUserResponse
import com.example.crossingschedule.feature.auth.data.repository.AuthApiService
import com.example.crossingschedule.feature.auth.data.repository.AuthProvider
import com.example.crossingschedule.feature.auth.data.repository.AuthProviderImpl
import com.example.crossingschedule.feature.auth.data.repository.AuthRepositoryImpl
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
    fun provideLoginRepository(
        loginRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository = loginRepositoryImpl

    @Provides
    fun provideLoginErrorMapper(
        failureToLoginErrorMapper: FailureToLoginErrorMapper
    ): Mapper<LoginError, Failure> = failureToLoginErrorMapper

    @Provides
    fun provideSignUpErrorMapper(
        failureToSignUpErrorMapper: FailureToSignUpErrorMapper
    ): Mapper<SignUpError, Failure> = failureToSignUpErrorMapper

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
        authenticateUserResponseToEitherMapper: AuthenticateUserResponseToEitherMapper
    ): Mapper<Either<Failure, Unit>, LoginUserResponse> = authenticateUserResponseToEitherMapper

}