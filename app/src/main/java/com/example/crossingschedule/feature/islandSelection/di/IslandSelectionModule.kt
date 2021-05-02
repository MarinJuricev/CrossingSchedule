package com.example.crossingschedule.feature.islandSelection.di

import com.example.crossingschedule.core.model.CrossingResponse
import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.islandSelection.data.mapper.IslandResponseToEitherMapper
import com.example.crossingschedule.feature.islandSelection.data.mapper.RemoteIslandInfoToIslandInfoMapper
import com.example.crossingschedule.feature.islandSelection.data.model.IslandResponse
import com.example.crossingschedule.feature.islandSelection.data.model.RemoteIslandInfo
import com.example.crossingschedule.feature.islandSelection.data.repository.IslandSelectionRepositoryImpl
import com.example.crossingschedule.feature.islandSelection.data.service.IslandSelectionApiService
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandInfo
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandSelectionFailure
import com.example.crossingschedule.feature.islandSelection.domain.repository.IslandSelectionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object IslandSelectionModule {

    @Provides
    fun provideIslandSelectionRepository(
        islandSelectionRepositoryImpl: IslandSelectionRepositoryImpl
    ): IslandSelectionRepository = islandSelectionRepositoryImpl

    @Provides
    fun provideIslandSelectionApiService(
        retrofit: Retrofit
    ): IslandSelectionApiService = retrofit.create(IslandSelectionApiService::class.java)

    @Provides
    fun provideIslandResponseToEitherMapper(
        islandResponseToEitherMapperImpl: IslandResponseToEitherMapper
    ): Mapper<Either<IslandSelectionFailure, List<IslandInfo>>, CrossingResponse<IslandResponse>> =
        islandResponseToEitherMapperImpl

    @Provides
    fun provideRemoteIslandInfoToIslandInfoMapper(
        remoteIslandInfoToIslandInfoMapper: RemoteIslandInfoToIslandInfoMapper
    ): Mapper<List<IslandInfo>, List<RemoteIslandInfo>> =
        remoteIslandInfoToIslandInfoMapper
}