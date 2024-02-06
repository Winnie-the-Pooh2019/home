package com.example.home.exceptions

import com.example.home.exceptions.core.HomeApplicationException
import org.springframework.http.HttpStatus

class VerificationTokenNotFoundException(message: String) : HomeApplicationException(HttpStatus.NOT_FOUND, message)