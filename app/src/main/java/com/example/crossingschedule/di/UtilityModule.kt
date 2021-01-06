package com.example.crossingschedule.di

import android.content.Context
import com.example.crossingschedule.presentation.util.IStringProvider
import com.example.crossingschedule.presentation.util.StringProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object UtilityModule {

    @Singleton
    @Provides
    fun provideStringProvider(@ApplicationContext context: Context): IStringProvider =
        StringProviderImpl(context = context)

}