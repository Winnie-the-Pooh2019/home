package com.example.home.service

import com.example.home.domain.dto.UserDto
import com.example.home.domain.model.User

interface UserService {
    fun saveUser(userDto: UserDto)

    fun existsUser(email: String): Boolean

    fun findAllUsers(): List<UserDto>
}