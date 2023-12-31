package com.example.messengerApp.presentation.ui.authentication.auth

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.messengerApp.R
import com.example.messengerApp.core.util.Resource
import com.example.messengerApp.data.remote.dto.UserRequestDto
import com.example.messengerApp.databinding.FragmentAuthBinding
import com.example.messengerApp.presentation.base.BaseFragment
import com.example.messengerApp.presentation.utils.Constants.PASSWORD_LENGTH
import com.example.messengerApp.presentation.utils.Validation.CODES.CODE_DIGITS
import com.example.messengerApp.presentation.utils.Validation.CODES.CODE_LENGTH
import com.example.messengerApp.presentation.utils.Validation.CODES.CODE_SPACES
import com.example.messengerApp.presentation.utils.Validation.CODES.CODE_UPPER_CASE
import com.example.messengerApp.presentation.utils.ext.makeSnackBarError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment
    : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {

    // TODO: remember me checkbox 

    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        setListeners()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerState.collect { state ->
                    when(state) {
                        is Resource.Success -> {
                            openMainActivity(binding.editTextEmail.text.toString())
                            binding.llLoading.visibility = GONE
                        }
                        is Resource.Empty -> {
                            emailErrorChanges()
                            passwordErrorChanges()
                        }
                        is Resource.Error -> {
                            showErrorSnackBar(state.message ?: getString(R.string.snackbar_register_error))
                            binding.llLoading.visibility = GONE
                        }
                        is Resource.Loading -> binding.llLoading.visibility = VISIBLE
                    }
                }
            }
        }
    }

    private fun setListeners() {
        with(binding) {
            buttonRegister.setOnClickListener { registerButton() }
            buttonGoogle.setOnClickListener { googleButtonAction() }
            textViewSignIn.setOnClickListener { openRegisterFragment() }
        }
    }

    private fun openRegisterFragment() {
        val direction = AuthFragmentDirections
            .actionAuthenticationFragmentToLoginFragment()

        navController.navigate(direction)
    }

    private fun googleButtonAction() {
        openMainActivity(binding.editTextEmail.text.toString())
    }

    private fun registerButton() {
        with(binding) {
            if (viewModel.validate(editTextEmail.text.toString(),
                    editTextPassword.text.toString())
            ) {
                viewModel.registerUser(
                    UserRequestDto(editTextEmail.text.toString(), editTextPassword.text.toString()),
                    checkboxRememberMe.isChecked
                )
            } else {
                editTextEmail.text.toString()
                    .ifEmpty { containerEmail.error = getString(R.string.invalid_email_address) }
                editTextPassword.text.toString()
                    .ifEmpty { containerPassword.error = getString(R.string.invalid_password) }
            }
        }
    }

    private fun showErrorSnackBar(message: String) {
        makeSnackBarError(requireContext(), binding.root, message)
    }

    private fun openMainActivity(email: String) {
        val direction = AuthFragmentDirections.startMainActivity(email)

        navController.navigate(direction)
    }

    private fun emailErrorChanges() {
        with(binding) {
            editTextEmail.doAfterTextChanged {
                val email = editTextEmail.text.toString()
                containerEmail.error =
                    if (!viewModel.emailIsValid(email))
                        getString(R.string.invalid_email_address)
                    else
                        null
            }
        }
    }

    private fun passwordErrorChanges() {
        with(binding) {
            editTextPassword.doAfterTextChanged {
                val password = binding.editTextPassword.text.toString()
                containerPassword.error =
                    when (viewModel.passwordIsValid(password)) {
                        CODE_SPACES -> getString(R.string.dont_use_spaces)
                        CODE_LENGTH -> getString(R.string.min_length_password, PASSWORD_LENGTH)
                        CODE_UPPER_CASE -> getString(R.string.contain_upper_case_chars)
                        CODE_DIGITS -> getString(R.string.contain_digit_chars)
                        else -> null
                    }
            }
        }
    }
}