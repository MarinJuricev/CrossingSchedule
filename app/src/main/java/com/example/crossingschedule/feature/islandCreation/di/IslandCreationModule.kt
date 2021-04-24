package com.example.crossingschedule.feature.islandCreation.di

import com.example.crossingschedule.feature.islandCreation.data.repository.IslandCreationRepositoryImpl
import com.example.crossingschedule.feature.islandCreation.data.service.IslandCreationApiService
import com.example.crossingschedule.feature.islandCreation.domain.repository.IslandCreationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Singleton

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
}