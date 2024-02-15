package com.example.home.domain.dto.auth

data class SignInRequest(
    val usernameOrEmail: String,
    val password: String
)
