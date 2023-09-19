package com.example.messengerApp.presentation.utils

import android.content.Context.MODE_PRIVATE

object Constants {

    // transition contact data
    const val TRANSITION_NAME_IMAGE = "TRANSITION_NAME_IMAGE"
    const val TRANSITION_NAME_CONTACT_NAME = "TRANSITION_NAME_FULL_NAME"
    const val TRANSITION_NAME_CAREER = "TRANSITION_NAME_CAREER"

    // sharedPreferences for remember me
    const val SHARED_PREF_NAME = "remember me"
    const val SHARED_PREF_MODE = MODE_PRIVATE
    const val PREF_CURRENT_ACCOUNT_TOKEN = "currentToken"
    const val PREF_AUTOLOGIN_EMAIL = "default"
    const val PREF_AUTOLOGIN_PASS = "default"

    // Validation
    const val PASSWORD_LENGTH = 8

    // Contact Generator
    const val COUNT_OF_CONTACTS = 15

    const val DEFAULT_NAME = "Hto Ya"

    // ViewPager screens
    enum class SCREENS {
        PROFILE_SCREEN,
        CONTACTS_SCREEN
    }
}