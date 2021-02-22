package com.example.crossingschedule.core.di

import com.example.crossingschedule.core.util.BaseUrlProvider
import com.example.crossingschedule.core.util.EncryptedPrefsService
import com.example.crossingschedule.core.util.EncryptedPrefsServiceImpl
import com.example.crossingschedule.feature.login.data.repository.AUTH_TOKEN_KEY
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.moshi.Moshi
import com.squareup.okhttp.Interceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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

    @Singleton
    @Provides
    fun provideEncryptedPrefsService(
        encryptedPrefsServiceImpl: EncryptedPrefsServiceImpl
    ): EncryptedPrefsService = encryptedPrefsServiceImpl

    //TODO Migrate to okhttp4
    @Singleton
    @Provides
    fun provideAuthInterceptor(
        encryptedPrefs: EncryptedPrefsService
    ): okhttp3.Interceptor =
        okhttp3.Interceptor { chain ->
            val original = chain.request()
            val token = encryptedPrefs.getStringValue(AUTH_TOKEN_KEY, "")
//            if (token.isEmpty()) {
//                chain.proceed()
//            }

            val newRequest = original.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()

            return@Interceptor chain.proceed(newRequest)
        }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: okhttp3.Interceptor
    ): okhttp3.OkHttpClient =
        okhttp3.OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authInterceptor)
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        baseUrlProvider: BaseUrlProvider,
        okHttpClient: okhttp3.OkHttpClient,
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