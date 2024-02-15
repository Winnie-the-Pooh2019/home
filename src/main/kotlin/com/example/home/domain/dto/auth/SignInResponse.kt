package com.example.home.domain.dto.auth

data class SignInResponse(
    val token: String,
    val refreshToken: String
)
