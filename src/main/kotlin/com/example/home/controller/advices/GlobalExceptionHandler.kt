package com.example.home.controller.advices

import com.example.home.domain.dto.HomeError
import com.example.home.exceptions.core.HomeApplicationException
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.persistence.PersistenceException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    private val logger: KLogger = KotlinLogging.logger {  }

    @ExceptionHandler(value = [
        HomeApplicationException::class,
        PersistenceException::class,
        DisabledException::class,
        BadCredentialsException::class,
        AuthenticationException::class,
        Exception::class
    ])
    fun processException(e: Exception): ResponseEntity<HomeError> {
        val status = when (e) {
            is HomeApplicationException -> e.httpStatus
            is PersistenceException -> HttpStatus.INTERNAL_SERVER_ERROR
            is DisabledException -> HttpStatus.LOCKED
            is BadCredentialsException -> HttpStatus.FORBIDDEN
            is AuthenticationException -> HttpStatus.NOT_FOUND
            else -> {
                logger.error { e.stackTrace }
                HttpStatus.I_AM_A_TEAPOT
            }
        }

        logger.error { "Status: $status, message: ${e.message}" }
        return ResponseEntity(HomeError(HomeError.StatusCode(status.value(), status.name), e.message), status)
    }
}