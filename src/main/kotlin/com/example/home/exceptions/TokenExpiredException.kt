package com.example.home.exceptions

import com.example.home.exceptions.core.HomeApplicationException
import org.springframework.http.HttpStatus

class TokenExpiredException(message: String) : HomeApplicationException(HttpStatus.GONE, message)