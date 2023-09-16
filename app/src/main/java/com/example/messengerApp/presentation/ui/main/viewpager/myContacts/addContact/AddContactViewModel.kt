package com.example.messengerApp.presentation.ui.main.viewpager.myContacts.addContact

import androidx.lifecycle.ViewModel
import com.example.messengerApp.domain.models.Contact
import com.example.messengerApp.domain.repository.interfaces.ContactService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddContactViewModel @Inject constructor(
    private val contactService: ContactService
) : ViewModel() {

    fun saveNewContact(
        name: String,
        photo: String,
        career: String,
        email: String,
        phone: String,
        address: String,
        dateOfBirthday: String,
    ) {
        contactService.addContact(
            Contact(
                name = name,
                photo = photo,
                career = career,
                email = email,
                phone = phone,
                address = address,
                dateOfBirthday = dateOfBirthday
            )
        )
    }

}