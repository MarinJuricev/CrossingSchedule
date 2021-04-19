package com.example.crossingschedule.feature.islandCreation.di

import com.example.crossingschedule.feature.islandCreation.data.repository.IslandCreationRepositoryImpl
import com.example.crossingschedule.feature.islandCreation.domain.repository.IslandCreationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object IslandCreationModule {

    @Provides
    fun provideIslandCreationRepository(
        islandCreationRepositoryImpl: IslandCreationRepositoryImpl
    ): IslandCreationRepository = islandCreationRepositoryImpl
}