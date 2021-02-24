package com.example.crossingschedule.core.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.crossingschedule.core.util.BaseUrlProvider
import com.example.crossingschedule.core.util.EncryptedPrefsService
import com.example.crossingschedule.core.util.EncryptedPrefsServiceImpl
import com.example.crossingschedule.feature.login.data.repository.AUTH_TOKEN_KEY
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideFirebaseFirestore() = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(
            HttpLoggingInterceptor.Level.BODY
        )

    @Singleton
    @Provides
    fun provideEncryptedPrefsService(
        encryptedPrefsServiceImpl: EncryptedPrefsServiceImpl
    ): EncryptedPrefsService = encryptedPrefsServiceImpl

    @Singleton
    @Provides
    fun provideAuthInterceptor(
        encryptedPrefs: EncryptedPrefsService
    ): Interceptor =
        Interceptor { chain ->
            val original = chain.request()
            val token = encryptedPrefs.getStringValue(AUTH_TOKEN_KEY, "")
            if (token.isEmpty())
                chain.proceed(original)

            val newRequest = original.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()

            return@Interceptor chain.proceed(newRequest)
        }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(httpLoggingInterceptor)
            .addInterceptor(authInterceptor)
            .addInterceptor(ChuckerInterceptor.Builder(context).build())
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        baseUrlProvider: BaseUrlProvider,
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrlProvider())
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .build()

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()
}