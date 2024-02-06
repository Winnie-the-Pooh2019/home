package com.example.home.exceptions

import com.example.home.exceptions.core.HomeApplicationException
import org.springframework.http.HttpStatus

class RoleNotFoundException(message: String) : HomeApplicationException(HttpStatus.NOT_FOUND, message)