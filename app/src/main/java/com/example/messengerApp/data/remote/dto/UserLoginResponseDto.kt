package com.example.messengerApp.data.remote.dto

data class UserLoginResponseDto(
    val user: UserDto,
    val accessToken: String,
    val refreshToken: String
)
