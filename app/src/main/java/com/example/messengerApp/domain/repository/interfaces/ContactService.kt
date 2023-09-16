package com.example.messengerApp.domain.repository.interfaces

import com.example.messengerApp.domain.models.Contact
import com.example.messengerApp.presentation.ui.main.viewpager.myContacts.multiselect.MultiSelectState
import kotlinx.coroutines.flow.StateFlow

interface ContactService {

    val contacts : StateFlow<List<Contact>>

    fun setContacts()

    fun setPhoneContacts()

    fun deleteContact(index: Int)

    fun restoreContact()

    fun addContact(contact: Contact)

    fun deleteSelectedItems(multiChoiceState: MultiSelectState<Contact>)

}