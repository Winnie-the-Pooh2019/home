package com.example.home.exceptions.core

import org.springframework.http.HttpStatus

sealed interface HomeException {
    val httpStatus: HttpStatus
    val message: String
}