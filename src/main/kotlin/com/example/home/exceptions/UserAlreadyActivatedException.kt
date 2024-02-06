package com.example.home.exceptions

import com.example.home.exceptions.core.HomeApplicationException
import org.springframework.http.HttpStatus

class UserAlreadyActivatedException(message: String) : HomeApplicationException(HttpStatus.CONFLICT, message)