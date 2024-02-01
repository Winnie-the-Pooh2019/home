package com.example.home.service

import com.example.home.domain.dto.RefreshTokenRequest
import com.example.home.domain.dto.SignInRequest
import com.example.home.domain.dto.SignInResponse
import com.example.home.domain.dto.UserDto
import com.example.home.domain.model.User

interface UserService {
    fun saveUser(userDto: UserDto): User

    fun signIn(signInRequest: SignInRequest): SignInResponse

    fun refreshToken(refreshTokenRequest: RefreshTokenRequest): SignInResponse?

    fun existsUserByEmail(email: String): Boolean

    fun existsUserByUserName(username: String): Boolean

    fun findAllUsers(): List<UserDto>
}