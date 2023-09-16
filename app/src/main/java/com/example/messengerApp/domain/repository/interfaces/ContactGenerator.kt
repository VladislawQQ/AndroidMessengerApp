package com.example.messengerApp.domain.repository.interfaces

import com.example.messengerApp.domain.models.Contact
import kotlinx.coroutines.flow.MutableStateFlow

interface ContactGenerator {

    fun generateContacts(): MutableStateFlow<List<Contact>>

    fun randomContact(): Contact

    fun getPhoneContacts(): MutableList<Contact>
}