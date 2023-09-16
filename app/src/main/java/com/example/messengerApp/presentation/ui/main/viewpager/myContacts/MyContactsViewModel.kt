package com.example.messengerApp.presentation.ui.main.viewpager.myContacts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerApp.domain.repository.interfaces.ContactService
import com.example.messengerApp.domain.models.Contact
import com.example.messengerApp.presentation.ui.main.viewpager.myContacts.model.ContactListItem
import com.example.messengerApp.presentation.ui.main.viewpager.myContacts.multiselect.MultiSelectHandler
import com.example.messengerApp.presentation.ui.main.viewpager.myContacts.multiselect.MultiSelectState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyContactsViewModel @Inject constructor(
    private val contactService : ContactService,
    private val contactMultiSelectHandler : MultiSelectHandler<Contact>
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(emptyList<ContactListItem>())
    val stateFlow = _stateFlow.asStateFlow()

    val isMultiselect : MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        viewModelScope.launch {
            contactMultiSelectHandler.setItemsFlow(viewModelScope, contactService.contacts)
            val combinedFlow = combine(
                contactService.contacts,
                contactMultiSelectHandler.listen(),
                ::merge
            )
            combinedFlow.collect { flow ->
                _stateFlow.value = flow
            }
        }
    }

    fun setPhoneContacts() = contactService.setPhoneContacts()
    fun deleteContact(index: Int) = contactService.deleteContact(index)
    fun deleteContact(contact: Contact) = contactService.deleteContact(contactService.contacts.value.indexOf(contact))

    /**
     * Delete all selected contacts
     */
    fun deleteSelectedItems() {
        viewModelScope.launch {
            val currentMultiChoiceState = contactMultiSelectHandler.listen().first()
            contactService.deleteSelectedItems(currentMultiChoiceState)
        }

        swapIsMultiselect()
        clearSelectedItems()
    }
    fun restoreContact() = contactService.restoreContact()

    fun addContact(contact: Contact) {
        contactService.addContact(contact)
    }
    fun clearSelectedItems() {
        contactMultiSelectHandler.clearAll()
    }
    fun toggle(contact: ContactListItem) {
        contactMultiSelectHandler.toggle(contact.contact)
    }

    fun contactsIsSelected() : Boolean = contactMultiSelectHandler.totalCheckedCount > 0

    fun swapIsMultiselect() {
        isMultiselect.value = isMultiselect.value == false
    }

    /**
     * Merge contacts from the model scope (singleton) and selection state from the view model scope.
     */
    private fun merge(contacts: List<Contact>, multiSelectState: MultiSelectState<Contact>): List<ContactListItem> {
        return contacts.map { contact ->
            ContactListItem(contact, multiSelectState.isChecked(contact))
        }
    }
}