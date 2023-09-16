package com.example.messengerApp.domain.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.messengerApp.presentation.utils.Constants.PREF_CURRENT_ACCOUNT_TOKEN
import com.example.messengerApp.presentation.utils.Constants.SHARED_PREF_MODE
import com.example.messengerApp.presentation.utils.Constants.SHARED_PREF_NAME
import com.example.messengerApp.presentation.utils.ext.logExt

object SharedPrefSource {

    private lateinit var sharedPreferences : SharedPreferences

    fun init(context : Context?) {
        sharedPreferences  = context?.getSharedPreferences(SHARED_PREF_NAME, SHARED_PREF_MODE) ?: return
    }

    fun setCurrentToken(token: String?) {
        val editor = sharedPreferences.edit()
        if (token == null)
            editor.remove(PREF_CURRENT_ACCOUNT_TOKEN)
        else
            editor.putString(PREF_CURRENT_ACCOUNT_TOKEN, token)
        editor.apply()
        logExt(token.toString())
    }

    fun getCurrentToken(): String? =
        sharedPreferences.getString(PREF_CURRENT_ACCOUNT_TOKEN, null)

}