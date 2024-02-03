package com.example.home.service

import com.example.home.domain.dto.RefreshTokenRequest
import com.example.home.domain.dto.SignInRequest
import com.example.home.domain.dto.SignInResponse
import com.example.home.domain.dto.UserDto
import com.example.home.domain.model.User
import org.springframework.mail.SimpleMailMessage

interface UserService {
    fun saveUser(userDto: UserDto): User

    fun signIn(signInRequest: SignInRequest): SignInResponse

    fun refreshToken(refreshTokenRequest: RefreshTokenRequest): SignInResponse?

    fun prepareEmail(user: User, url: String): SimpleMailMessage

    fun activateUser(token: String): User

    fun existsUserByEmail(email: String): Boolean

    fun existsUserByUserName(username: String): Boolean

    fun userExists(userDto: UserDto): Boolean

    fun findAllUsers(): List<UserDto>
}