package com.example.crossingschedule.core.di

import android.content.Context
import com.example.crossingschedule.core.util.IStringProvider
import com.example.crossingschedule.core.util.StringProviderImpl
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