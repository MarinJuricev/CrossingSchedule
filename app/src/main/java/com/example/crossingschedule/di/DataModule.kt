package com.example.crossingschedule.di

import com.example.crossingschedule.data.mapper.CrossingTodosToDefaultCrossingTodosMapper
import com.example.crossingschedule.data.repository.ActivitiesRepositoryImpl
import com.example.crossingschedule.domain.core.Mapper
import com.example.crossingschedule.domain.model.CrossingTodo
import com.example.crossingschedule.domain.repository.ActivitiesRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideFirebaseFirestore() = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideActivitiesRepository(
        firebaseFireStore: FirebaseFirestore,
        crossingTodosToDefaultCrossingTodosMapper: Mapper<List<CrossingTodo>, List<CrossingTodo>>
    ): ActivitiesRepository =
        ActivitiesRepositoryImpl(
            firebaseFireStore,
            crossingTodosToDefaultCrossingTodosMapper
        )

    @Provides
    fun providesCrossingTodosToDefaultCrossingTodosMapper(
        crossingTodosToDefaultCrossingTodosMapper: CrossingTodosToDefaultCrossingTodosMapper
    ): Mapper<List<CrossingTodo>, List<CrossingTodo>> = crossingTodosToDefaultCrossingTodosMapper
}