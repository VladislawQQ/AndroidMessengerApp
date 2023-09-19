package com.example.messengerApp.presentation.ui.authentication.login

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.messengerApp.R
import com.example.messengerApp.core.util.Resource
import com.example.messengerApp.data.remote.dto.UserRequestDto
import com.example.messengerApp.databinding.FragmentLoginBinding
import com.example.messengerApp.presentation.base.BaseFragment
import com.example.messengerApp.presentation.utils.ext.makeSnackBarError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment
    : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerState.collect { state ->
                    when (state) {
                        is Resource.Success -> {
                            openMainActivity(binding.editTextEmail.text.toString())
                            binding.llLoading.visibility = View.GONE
                        }
                        is Resource.Empty -> {
                            emailErrorChanges()
                            passwordChanges()
                        }
                        is Resource.Error -> {
                            showErrorSnackBar(state.message ?: getString(R.string.snackbar_login_error))
                            binding.llLoading.visibility = View.GONE
                        }
                        is Resource.Loading -> binding.llLoading.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun showErrorSnackBar(message: String) {
        makeSnackBarError(requireContext(), binding.root, message)
    }

    private fun setListeners() {
        with(binding) {
            textViewSignUp.setOnClickListener { openRegisterFragment() }
            buttonLogin.setOnClickListener { login() }
        }
    }

    private fun login() {
        with(binding) {
            if (viewModel.validate(editTextEmail.text.toString())
            ) {
                viewModel.loginUser(
                    UserRequestDto(
                        editTextEmail.text.toString(),
                        editTextPassword.text.toString()
                    ),
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

    private fun openRegisterFragment() {
        val direction = LoginFragmentDirections
            .actionLoginFragmentToAuthenticationFragment()

        navController.navigate(direction)
    }

    private fun openMainActivity(email: String) {
        val direction = LoginFragmentDirections
            .actionLoginFragmentToMainActivity(email)

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

    private fun passwordChanges() {
        with(binding) {
            editTextPassword.doAfterTextChanged {
                containerPassword.error = null
            }
        }
    }
}