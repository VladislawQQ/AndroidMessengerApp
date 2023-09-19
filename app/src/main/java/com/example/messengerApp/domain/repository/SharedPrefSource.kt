package com.example.messengerApp.domain.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.messengerApp.data.remote.dto.UserRequestDto
import com.example.messengerApp.presentation.utils.Constants.PREF_AUTOLOGIN_EMAIL
import com.example.messengerApp.presentation.utils.Constants.PREF_AUTOLOGIN_PASS
import com.example.messengerApp.presentation.utils.Constants.PREF_CURRENT_ACCOUNT_TOKEN
import com.example.messengerApp.presentation.utils.Constants.SHARED_PREF_MODE
import com.example.messengerApp.presentation.utils.Constants.SHARED_PREF_NAME

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
    }

    fun getCurrentToken(): String? =
        sharedPreferences.getString(PREF_CURRENT_ACCOUNT_TOKEN, null)

    fun setAutoLoginData(body : UserRequestDto) {
        val editor = sharedPreferences.edit()
        editor.putString(PREF_AUTOLOGIN_EMAIL, body.email)
        editor.putString(PREF_AUTOLOGIN_PASS, body.password)
        editor.apply()
    }

    fun getAutoLoginData() = UserRequestDto(
        sharedPreferences.getString(PREF_AUTOLOGIN_EMAIL, null),
        sharedPreferences.getString(PREF_AUTOLOGIN_PASS, null)
    )

}