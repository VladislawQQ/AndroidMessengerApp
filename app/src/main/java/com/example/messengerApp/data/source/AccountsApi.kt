package com.example.messengerApp.data.source

import com.example.messengerApp.data.remote.dto.ContactRequestDto
import com.example.messengerApp.data.remote.dto.UserEditDto
import com.example.messengerApp.data.remote.dto.UserRequestDto
import com.example.messengerApp.data.remote.dto.UserResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AccountsApi {

    @POST("users")
    suspend fun registerUser(
        @Body body: UserRequestDto
    ): UserResponseDto

    @POST("login")
    suspend fun loginUser(
        @Body body: UserRequestDto
    ): UserResponseDto

    @POST("refresh")
    suspend fun refreshToken(@Header("refreshToken") refreshToken: String?): UserResponseDto

    @GET("users/{userId}")
    suspend fun getUser(
        @Path("userId") userId: String,
        @Header("Authorization") accessToken: String
    ): UserResponseDto

    @PUT("users/{userId}")
    suspend fun editUser(
        @Path("userId") userId: String,
        @Header("Authorization") accessToken: String,
        @Header ("Content-type") contentType: String = "application/json",
        @Body body: UserEditDto,
    ): UserResponseDto

    @GET("users")
    suspend fun getAllUsers(@Header("Authorization") accessToken: String): UserResponseDto

    @PUT("users/{userId}/contacts")
    suspend fun addContact(
        @Path("userId") userId: String,
        @Header("Authorization") accessToken: String,
        @Body contactRequest: ContactRequestDto
    ): UserResponseDto

    @DELETE("users/{userId}/contacts/{contactId}")
    suspend fun deleteContact(
        @Path("userId") userId: String,
        @Header("Authorization") accessToken: String,
        @Path("contactId") contactId: String,
    ): UserResponseDto

    @GET("users/{userId}/contacts")
    suspend fun getUserContacts(
        @Path("userId") userId: String,
        @Header("Authorization") accessToken: String
    ): UserResponseDto
}