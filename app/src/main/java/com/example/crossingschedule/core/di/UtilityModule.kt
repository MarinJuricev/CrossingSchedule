package com.example.crossingschedule.core.di

import android.content.Context
import com.example.crossingschedule.core.util.DateHandlerImpl
import com.example.crossingschedule.core.util.DateHandler
import com.example.crossingschedule.core.util.StringProvider
import com.example.crossingschedule.core.util.StringProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilityModule {

    @Singleton
    @Provides
    fun provideStringProvider(@ApplicationContext context: Context): StringProvider =
        StringProviderImpl(context = context)

    @Singleton
    @Provides
    fun provideDateHandler(dateHandler: DateHandlerImpl): DateHandler =
        dateHandler

}