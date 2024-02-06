package com.example.home.exceptions

import com.example.home.exceptions.core.HomeApplicationException
import org.springframework.http.HttpStatus

class UserNotFoundException(message: String) : HomeApplicationException(HttpStatus.NOT_FOUND, message)