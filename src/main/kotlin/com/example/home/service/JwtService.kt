package com.example.home.service

import io.jsonwebtoken.JwtException
import org.springframework.security.core.userdetails.UserDetails
import kotlin.jvm.Throws

interface JwtService {
    fun generateToken(userDetails: UserDetails): String

    fun generateRefreshToken(extraClaims: Map<String, Any>, userDetails: UserDetails): String

    @Throws(exceptionClasses = [JwtException::class])
    fun extractUserName(token: String): String

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean
}