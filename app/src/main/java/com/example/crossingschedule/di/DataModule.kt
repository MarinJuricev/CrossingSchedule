package com.example.crossingschedule.di

import com.example.crossingschedule.data.repository.ActivitiesRepositoryImpl
import com.example.crossingschedule.domain.repository.ActivitiesRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideFirebaseFirestore() = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideActivitiesRepository(
        firebaseFireStore: FirebaseFirestore
    ): ActivitiesRepository = ActivitiesRepositoryImpl(firebaseFireStore)
}