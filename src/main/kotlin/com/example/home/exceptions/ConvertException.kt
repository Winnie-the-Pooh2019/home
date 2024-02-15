package com.example.home.exceptions

import com.example.home.exceptions.core.HomeApplicationException
import org.springframework.http.HttpStatus

class ConvertException(message: String) : HomeApplicationException(HttpStatus.BAD_REQUEST, message)