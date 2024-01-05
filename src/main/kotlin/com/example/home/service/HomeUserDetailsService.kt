package com.example.home.service

import com.example.home.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class HomeUserDetailsService(
    @Autowired
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(email: String?): UserDetails {
        if (email == null)
            throw UsernameNotFoundException("email cannot be null")

        val user = userRepository.findByEmail(email)
            .orElseThrow { UsernameNotFoundException("no user with such name found") }

        val authorities = user.roles.map { SimpleGrantedAuthority(it.name) }.toSet()

        return org.springframework.security.core.userdetails.User(
            email,
            user.password,
            authorities
        )
    }
}