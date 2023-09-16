package com.example.messengerApp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Contact (
    var id: Long = UUID.randomUUID().mostSignificantBits,
    val photo: String? = null,
    val name: String? = null,
    val career: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val dateOfBirthday: String? = null,
) : Parcelable