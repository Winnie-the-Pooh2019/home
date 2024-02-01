package com.example.home.domain.dto

data class SignInResponse(
    val token: String,
    val refreshToken: String
)
