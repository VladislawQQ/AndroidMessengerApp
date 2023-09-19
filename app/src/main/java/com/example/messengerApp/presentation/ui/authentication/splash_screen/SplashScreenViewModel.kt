package com.example.messengerApp.presentation.ui.authentication.splash_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerApp.core.util.AccountAlreadyExistsException
import com.example.messengerApp.core.util.BackendException
import com.example.messengerApp.core.util.ConnectionException
import com.example.messengerApp.core.util.Resource
import com.example.messengerApp.data.remote.dto.UserLoginResponseDto
import com.example.messengerApp.data.source.RetrofitAccountsSource
import com.example.messengerApp.domain.repository.SharedPrefSource
import com.example.messengerApp.presentation.ui.authentication.auth.ErrorMessages.ACCOUNT_ALREADY_EXIST_ERROR
import com.example.messengerApp.presentation.ui.authentication.auth.ErrorMessages.AUTOLOGIN_ERROR
import com.example.messengerApp.presentation.ui.authentication.auth.ErrorMessages.BACKEND_ERROR
import com.example.messengerApp.presentation.ui.authentication.auth.ErrorMessages.CONNECTION_ERROR
import com.example.messengerApp.presentation.ui.authentication.auth.ErrorMessages.REGISTER_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val accountsSource: RetrofitAccountsSource
) : ViewModel() {

    private val _registerStateFlow = MutableStateFlow<Resource<UserLoginResponseDto>>(Resource.Empty())
    val registerState = _registerStateFlow.asStateFlow()

    fun autoLogin() =
        viewModelScope.launch(Dispatchers.IO) {
            _registerStateFlow.value = Resource.Loading()
            if (SharedPrefSource.getCurrentToken() != null) {
                try {
                    val body = SharedPrefSource.getAutoLoginData()
                    val response = accountsSource.loginUser(body)
                    when (response.code) {
                        200 -> {
                            _registerStateFlow.value = Resource.Success(response.data)
                        }
                        401 -> {
                            _registerStateFlow.value = Resource.Error(response.message)
                        }
                    }
                } catch (e: BackendException) {
                    _registerStateFlow.value = Resource.Error(message = BACKEND_ERROR)
                } catch (e: AccountAlreadyExistsException) {
                    _registerStateFlow.value =
                        Resource.Error(message = ACCOUNT_ALREADY_EXIST_ERROR)
                } catch (e: ConnectionException) {
                    _registerStateFlow.value =
                        Resource.Error(message = CONNECTION_ERROR)
                } catch (e: Exception) {
                    _registerStateFlow.value =
                        Resource.Error(message = REGISTER_ERROR)
                }
            } else {
                _registerStateFlow.value =
                    Resource.Error(message = AUTOLOGIN_ERROR)
            }
        }
}