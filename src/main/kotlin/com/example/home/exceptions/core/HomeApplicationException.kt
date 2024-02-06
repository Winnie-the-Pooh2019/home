package com.example.home.exceptions.core

import org.springframework.http.HttpStatus

open class HomeApplicationException(override val httpStatus: HttpStatus, override val message: String) : HomeException,
    Exception(message)