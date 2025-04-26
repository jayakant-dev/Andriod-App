package com.example.artsy_final

data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val fname: String, val email: String, val password: String)


data class LoginResponse(
    val success: Boolean,
    val message: String,
    val user: UserInfo?
)

data class RegisterResponse(
    val success: Boolean,
    val message: String,
    val user: UserInfo?
)

data class UserInfo(
    val name: String,
    val email: String,
    val gravitarID: String
)

