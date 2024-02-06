package com.example.home.service.impl

import com.example.home.exceptions.UserNotFoundException
import com.example.home.repository.UserRepository
import com.example.home.service.HomeUserDetailsService
import com.example.home.utils.HomeAppUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class HomeUserDetailsServiceImpl(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val homeAppUtils: HomeAppUtils
) : HomeUserDetailsService {
    override fun loadUserByUserNameOrEmail(usernameOrEmail: String?): UserDetails {
        if (usernameOrEmail == null)
            throw UserNotFoundException("username cannot be null")

        val user =
            (if (homeAppUtils.isEmail(usernameOrEmail)) userRepository.findByEmail(usernameOrEmail)
            else userRepository.findByUserName(usernameOrEmail))
                .orElseThrow { UserNotFoundException("Invalid username or email") }
        return user
    }

    override fun loadUserByUsername(username: String?): UserDetails = loadUserByUserNameOrEmail(username)
}