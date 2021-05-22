package com.example.crossingschedule.feature.islandCreation.di

import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.islandCreation.data.repository.IslandCreationRepositoryImpl
import com.example.crossingschedule.feature.islandCreation.data.service.IslandCreationApiService
import com.example.crossingschedule.feature.islandCreation.domain.model.IslandCreationFailure
import com.example.crossingschedule.feature.islandCreation.domain.repository.IslandCreationRepository
import com.example.crossingschedule.feature.islandCreation.presentation.mapper.IslandCreationFailureToErrorMapper
import com.example.crossingschedule.feature.islandCreation.presentation.model.IslandCreationError
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object IslandCreationModule {

    @Provides
    fun provideIslandCreationRepository(
        islandCreationRepositoryImpl: IslandCreationRepositoryImpl
    ): IslandCreationRepository = islandCreationRepositoryImpl

    @Provides
    fun provideIslandCreationApiService(
        retrofit: Retrofit
    ): IslandCreationApiService = retrofit.create(IslandCreationApiService::class.java)

    @Provides
    fun provideIslandCreationFailureToErrorMapper(
        islandCreationFailureToErrorMapper: IslandCreationFailureToErrorMapper
    ): Mapper<IslandCreationError, IslandCreationFailure> = islandCreationFailureToErrorMapper
}