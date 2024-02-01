package com.example.home.domain.dto

data class SignInRequest(
    val usernameOrEmail: String,
    val password: String
)
