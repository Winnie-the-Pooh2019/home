package com.example.home.service

import com.example.home.repository.UserRepository
import jakarta.transaction.Transactional
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

    @Transactional
    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null)
            throw UsernameNotFoundException("username cannot be null")

        val user = userRepository.findByUsername(username)
            .orElseThrow { UsernameNotFoundException("no user with such name found") }

        val authorities = user.roles.map { SimpleGrantedAuthority(it.name) }.toSet()

        return org.springframework.security.core.userdetails.User(
            username,
            user.password,
            authorities
        )
    }
}