package com.example.messengerApp.presentation.utils.ext

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.example.messengerApp.R
import com.google.android.material.snackbar.Snackbar

fun makeSnackBarError(
    context : Context,
    binding : View,
    message : String
) {
    Snackbar.make(binding, message.replaceFirstChar { it.titlecase() }, Snackbar.LENGTH_LONG)
        .setActionTextColor(
            ContextCompat.getColor(
                context,
                R.color.contacts_activity_class_snackbar_action_color
            )
        )
        .setTextColor(
            ContextCompat.getColor(
                context,
                R.color.contacts_activity_class_snackbar_text_color
            )
        )
        .show()
}