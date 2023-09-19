package com.example.messengerApp.data.source

import com.example.messengerApp.data.remote.dto.ContactRequestDto
import com.example.messengerApp.data.remote.dto.UserEditDto
import com.example.messengerApp.data.remote.dto.UserRequestDto
import com.example.messengerApp.data.remote.dto.UserResponseDto
import javax.inject.Inject

class RetrofitAccountsSource @Inject constructor(
    private val accountsApi: AccountsApi,
) {

    suspend fun registerUser(body: UserRequestDto) : UserResponseDto {
        return accountsApi.registerUser(body = body)
    }

    suspend fun loginUser(body: UserRequestDto) : UserResponseDto {
        return accountsApi.loginUser(body = body)
    }

    suspend fun refreshToken(refreshToken: String?) : UserResponseDto {
        return accountsApi.refreshToken(refreshToken)
    }

    suspend fun getUser(userId: String, accessToken: String) : UserResponseDto {
        return accountsApi.getUser(userId, accessToken)
    }

    suspend fun editUser(
        userId: String,
        accessToken: String,
        body: UserEditDto,
    ) : UserResponseDto {
        return accountsApi.editUser(userId, accessToken, body = body)
    }

    suspend fun getAllUsers(accessToken: String) : UserResponseDto  {
        return accountsApi.getAllUsers(accessToken)
    }

    suspend fun addContact(
        userId: String,
        accessToken: String,
        contactRequest: ContactRequestDto,
    ) : UserResponseDto {
        return accountsApi.addContact(userId, accessToken, contactRequest)
    }

    suspend fun deleteContact(
        userId: String,
        accessToken: String,
        contactId: String,
    ) : UserResponseDto {
        return accountsApi.deleteContact(userId, accessToken, contactId)
    }

    suspend fun getUserContacts(userId: String, accessToken: String) : UserResponseDto {
        return accountsApi.getUserContacts(userId, accessToken)
    }

}