package com.example.home.domain.dto

data class HomeError(
    val status: StatusCode,
    val message: String?
) {
    data class StatusCode(
        val code: Int,
        val name: String
    )
}
