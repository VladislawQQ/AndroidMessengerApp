package com.example.messengerApp.presentation.ui.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerApp.core.util.AccountAlreadyExistsException
import com.example.messengerApp.core.util.BackendException
import com.example.messengerApp.core.util.ConnectionException
import com.example.messengerApp.core.util.Resource
import com.example.messengerApp.data.remote.dto.UserLoginResponseDto
import com.example.messengerApp.data.remote.dto.UserRequestDto
import com.example.messengerApp.data.source.RetrofitAccountsSource
import com.example.messengerApp.domain.repository.SharedPrefSource
import com.example.messengerApp.presentation.ui.authentication.auth.ErrorMessages
import com.example.messengerApp.presentation.utils.Validation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountsSource: RetrofitAccountsSource,
) : ViewModel() {

    private val _registerStateFlow =
        MutableStateFlow<Resource<UserLoginResponseDto>>(Resource.Empty())
    val registerState = _registerStateFlow.asStateFlow()

    fun loginUser(body: UserRequestDto, saveToken: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            _registerStateFlow.value = Resource.Loading()
            try {
                val response = accountsSource.loginUser(body)
                when (response.code) {
                    200 -> {
                        if (saveToken) {
                            SharedPrefSource.setCurrentToken(response.data.accessToken)
                            SharedPrefSource.setAutoLoginData(body)
                        }
                        _registerStateFlow.value = Resource.Success(response.data)
                    }
                    401 -> {
                        _registerStateFlow.value = Resource.Error(response.message)
                    }
                }
            } catch (e: BackendException) {
                _registerStateFlow.value =
                    Resource.Error(message = ErrorMessages.BACKEND_ERROR)
            } catch (e: AccountAlreadyExistsException) {
                _registerStateFlow.value =
                    Resource.Error(message = ErrorMessages.ACCOUNT_ALREADY_EXIST_ERROR)
            } catch (e: ConnectionException) {
                _registerStateFlow.value =
                    Resource.Error(message = ErrorMessages.CONNECTION_ERROR)
            } catch (e: Exception) {
                _registerStateFlow.value =
                    Resource.Error(message = ErrorMessages.REGISTER_ERROR)
            }
        }

    /**
     * Check if email is valid with Patterns.EMAIL_ADDRESS
     * if !isValid return "Invalid Email Address"
     * else return null
     */
    fun emailIsValid(email: String): Boolean = Validation.emailIsValid(email)

    /**
     * Check if password is valid with regex
     * if !isValid return String
     * else return null
     */
    fun passwordIsValid(password: String): Validation.CODES? = Validation.passwordIsValid(password)

    fun validate(email: String, password: String): Boolean {
        return emailIsValid(email) && passwordIsValid(password) == null
    }
}