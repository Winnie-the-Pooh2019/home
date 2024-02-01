package com.example.home.service.impl

import com.example.home.repository.UserRepository
import com.example.home.service.HomeUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class HomeUserDetailsServiceImpl(
    @Autowired
    private val userRepository: UserRepository
) : HomeUserDetailsService {
    override fun loadUserByUserName(username: String?): UserDetails {
        if (username == null)
            throw UsernameNotFoundException("username cannot be null")

        val user = userRepository.findByUserName(username)
            .orElseThrow { UsernameNotFoundException("no user with such name found") }

        return user
    }

    override fun loadUserByUsername(username: String?): UserDetails = loadUserByUserName(username)
}