package com.example.home.controller.advices

import com.example.home.domain.dto.HomeError
import com.example.home.exceptions.TokenExpiredException
import com.example.home.exceptions.UserActivationException
import com.example.home.exceptions.jpa.UserAlreadyExistsException
import com.example.home.exceptions.jpa.VerificationTokenNotFoundException
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.JwtException
import jakarta.persistence.PersistenceException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    private val logger: KLogger = KotlinLogging.logger {  }

    @ExceptionHandler(value = [UserAlreadyExistsException::class])
    fun badRequest(e: Exception): ResponseEntity<HomeError> {
        val status = HttpStatus.BAD_REQUEST
        logger.error { "Status: $status, message: ${e.message}" }

        return ResponseEntity(HomeError(status.value(), e.message), status)
    }

    @ExceptionHandler(value = [UserActivationException::class, PersistenceException::class])
    fun internalServerError(e: Exception): ResponseEntity<HomeError> {
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        logger.error { "Status: $status, message: ${e.message}" }

        return ResponseEntity(HomeError(status.value(), e.message), status)
    }

    @ExceptionHandler(value = [AuthenticationException::class, TokenExpiredException::class, JwtException::class])
    fun unauthorized(e: Exception): ResponseEntity<HomeError> {
        val status = HttpStatus.UNAUTHORIZED
        logger.error { "Status: $status, message: ${e.message}" }

        return ResponseEntity(HomeError(status.value(), e.message), status)
    }

    @ExceptionHandler(value = [VerificationTokenNotFoundException::class])
    fun notFound(e: Exception): ResponseEntity<HomeError> {
        val status = HttpStatus.NOT_FOUND
        logger.error { "Status: $status, message: ${e.message}" }

        return ResponseEntity(HomeError(status.value(), e.message), status)
    }
}