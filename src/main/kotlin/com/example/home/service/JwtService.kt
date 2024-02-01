package com.example.home.service

import org.springframework.security.core.userdetails.UserDetails

interface JwtService {
    fun generateToken(userDetails: UserDetails): String

    fun extractUserName(token: String): String

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean
}