package com.example.home.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

interface HomeUserDetailsService : UserDetailsService {
    fun loadUserByUserNameOrEmail(usernameOrEmail: String?): UserDetails
}