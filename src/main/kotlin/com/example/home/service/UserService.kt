package com.example.home.service

import com.example.home.domain.dto.UserDto

interface UserService {
    fun saveUser(userDto: UserDto)

    fun existsUserByEmail(email: String): Boolean

    fun existsUserByUsername(username: String): Boolean

    fun findAllUsers(): List<UserDto>
}