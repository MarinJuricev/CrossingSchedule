package com.example.crossingschedule.core.util

interface EncryptedPrefsService {
    fun saveValue(key:String, value: String)
    fun getStringValue(key:String, defaultValue: String): String
}