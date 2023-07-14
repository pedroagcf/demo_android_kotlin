package com.ufc.appdemo

import android.content.Context
import android.util.Patterns

object Util {
    fun saveUserId(context: Context, uuid: String?) {
        val preferences = context.getSharedPreferences("APP", Context.MODE_PRIVATE)
        preferences.edit().putString("UIID", uuid).apply()
    }

    fun validateNameEmailPassword(name:String, email: String, password: String): Boolean {
        return when {
            name.isEmpty() || email.isEmpty() || password.isEmpty() -> {
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                false
            }
            password.length < 6 -> {
                false
            }
            else -> true
        }
    }

    fun validateEmailPassword(email: String, password: String): Boolean {
        return when {
            email.isEmpty() || password.isEmpty() -> {
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                false
            }
            password.length < 6 -> {
                false
            }
            else -> true
        }
    }
}