package com.example.home.service

import com.example.home.domain.dto.UserDto
import com.example.home.domain.model.User

interface UserService {
    fun saveUser(userDto: UserDto): User

    fun existsUserByEmail(email: String): Boolean

    fun existsUserByUserName(username: String): Boolean

    fun findAllUsers(): List<UserDto>
}