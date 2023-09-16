package com.example.messengerApp.data.remote.dto


data class UserResponseDto(
    val status: String? = null,
    val code: Int? = null,
    val message: String? = null,
    val data: UserLoginResponseDto
)