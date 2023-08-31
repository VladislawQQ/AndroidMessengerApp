package com.example.messengerApp.ui.main.contactProfile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.example.messengerApp.R
import com.example.messengerApp.base.BaseFragment
import com.example.messengerApp.data.models.Contact
import com.example.messengerApp.databinding.FragmentContactProfileBinding
import com.example.messengerApp.ui.utils.constants.Constants.TRANSITION_NAME_CAREER
import com.example.messengerApp.ui.utils.constants.Constants.TRANSITION_NAME_CONTACT_NAME
import com.example.messengerApp.ui.utils.constants.Constants.TRANSITION_NAME_IMAGE
import com.example.messengerApp.ui.utils.ext.setContactPhoto

class ContactProfileFragment
    : BaseFragment<FragmentContactProfileBinding>(FragmentContactProfileBinding::inflate){

    private val viewModel : ContactProfileModelView by viewModels()

    // TODO: from popBackStack() to navigateUp()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachAnimation()
        bindContactInfo(viewModel.contact)
        setListeners()
    }

    private fun attachAnimation() {
        with(binding) {
            fragmentMyProfileImageViewProfilePhoto.transitionName = TRANSITION_NAME_IMAGE
            fragmentMyProfileTextViewProfileName.transitionName = TRANSITION_NAME_CONTACT_NAME
            fragmentMyProfileTextViewCareer.transitionName = TRANSITION_NAME_CAREER
        }

        val animation = TransitionInflater.from(requireContext()).inflateTransition(R.transition.transition_move)

        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    private fun setListeners() {
        binding.fragmentMyProfileTextViewBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun bindContactInfo(contact: Contact) {
        with(binding) {
            fragmentMyProfileTextViewProfileName.text = contact.name
            fragmentMyProfileTextViewCareer.text = contact.career
            fragmentMyProfileImageViewProfilePhoto.setContactPhoto(contact.photo)
        }
    }
}