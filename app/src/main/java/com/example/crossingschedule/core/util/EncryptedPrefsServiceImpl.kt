package com.example.crossingschedule.core.util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class EncryptedPrefsServiceImpl @Inject constructor(
    @ApplicationContext applicationContext: Context
) : EncryptedPrefsService {
    private val mainKey = MasterKey.Builder(applicationContext)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPrefsFile: String = "CROSSING-SCHEDULE-FILE-NAME"
    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        applicationContext,
        sharedPrefsFile,
        mainKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )


    override fun saveValue(key: String, value: String) {
        sharedPreferences.edit()
            .putString(key, value)
            .apply()
    }

    override fun getStringValue(key: String, defaultValue: String): String {
        val preferenceValue = sharedPreferences.getString(key, defaultValue)
        // Just plain stupid, the defaultValue from the above API should be guaranteed
        return preferenceValue ?: defaultValue
    }
}
