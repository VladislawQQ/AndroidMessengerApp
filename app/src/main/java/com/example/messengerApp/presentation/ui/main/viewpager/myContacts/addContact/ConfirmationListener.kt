package com.example.messengerApp.presentation.ui.main.viewpager.myContacts.addContact

import com.example.messengerApp.domain.models.Contact

interface ConfirmationListener {
    fun onConfirmButtonClicked(contact: Contact)
}