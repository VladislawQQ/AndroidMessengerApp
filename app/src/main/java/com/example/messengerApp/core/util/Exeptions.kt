package com.example.messengerApp.core.util

open class AppException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
}

class AccountAlreadyExistsException(
    cause: Throwable
) : AppException(cause = cause)

open class BackendException(
    val code: Int,
    message: String
) : AppException(message)

class ConnectionException(cause: Throwable) : AppException(cause = cause)