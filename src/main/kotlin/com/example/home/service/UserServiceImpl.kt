package com.example.home.service

import com.example.home.domain.dto.UserDto
import com.example.home.domain.model.User
import com.example.home.repository.RoleRepository
import com.example.home.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val roleRepository: RoleRepository,
    @Autowired
    private val passwordEncoder: PasswordEncoder
) : UserService {

    override fun saveUser(userDto: UserDto) {
        val user = User()
        val role = roleRepository.findByName("ROLE_USER").orElseThrow { Exception("No role ROLE_USER exists") }

        user
            .addUsername(userDto.username)
            .addEmail(userDto.email)
            .addPassword(passwordEncoder.encode(userDto.password))
            .addRoles(setOf(role))

        userRepository.save(user)
    }

    override fun existsUserByEmail(email: String): Boolean = userRepository.findByEmail(email).isPresent
    override fun existsUserByUsername(username: String): Boolean = userRepository.findByUsername(username).isPresent

    override fun findAllUsers(): List<UserDto> = userRepository.findAll().map { UserDto(it.email, it.password) }
}