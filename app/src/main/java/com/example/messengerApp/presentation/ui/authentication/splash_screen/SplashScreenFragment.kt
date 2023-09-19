package com.example.messengerApp.presentation.ui.authentication.splash_screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.messengerApp.core.util.Resource
import com.example.messengerApp.databinding.FragmentSplashScreenBinding
import com.example.messengerApp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenFragment : BaseFragment<FragmentSplashScreenBinding>(FragmentSplashScreenBinding::inflate) {

    private val viewModel : SplashScreenViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerState.collect { state ->
                    when(state) {
                        is Resource.Success -> {
                            if (state.data?.user?.email != null) {
                                openMainActivity(state.data.user.email)
                            } else {
                                openLoginFragment()
                            }
                        }
                        is Resource.Empty -> viewModel.autoLogin()
                        is Resource.Error -> openLoginFragment()
                        is Resource.Loading -> delay(200)
                    }
                }
            }
        }
    }

    private fun openLoginFragment() {
        val direction = SplashScreenFragmentDirections
            .actionSplashScreenFragmentToLoginFragment()

        navController.navigate(direction)
    }

    private fun openMainActivity(email : String) {
        val direction = SplashScreenFragmentDirections
            .actionSplashScreenFragmentToMainActivity(email)

        navController.navigate(direction)
    }
}