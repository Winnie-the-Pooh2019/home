package com.example.home.service

import com.example.home.domain.dto.RefreshTokenRequest
import com.example.home.domain.dto.SignInRequest
import com.example.home.domain.dto.SignInResponse
import com.example.home.domain.dto.UserDto
import com.example.home.domain.model.User
import com.example.home.exceptions.TokenExpiredException
import com.example.home.exceptions.VerificationTokenExpiredException
import com.example.home.exceptions.jpa.RoleNotFoundException
import com.example.home.exceptions.jpa.UserAlreadyExistsException
import com.example.home.exceptions.jpa.UserNotFoundException
import com.example.home.exceptions.jpa.VerificationTokenNotFoundException
import io.jsonwebtoken.JwtException
import jakarta.persistence.PersistenceException
import org.apache.tomcat.websocket.AuthenticationException
import org.springframework.mail.SimpleMailMessage

interface UserService {
    @Throws(exceptionClasses = [UserAlreadyExistsException::class, RoleNotFoundException::class, PersistenceException::class])
    fun saveUser(userDto: UserDto): User

    @Throws(exceptionClasses = [UserNotFoundException::class, AuthenticationException::class, PersistenceException::class])
    fun signIn(signInRequest: SignInRequest): SignInResponse

    @Throws(exceptionClasses = [JwtException::class, TokenExpiredException::class, UserNotFoundException::class, PersistenceException::class])
    fun refreshToken(refreshTokenRequest: RefreshTokenRequest): SignInResponse

    @Throws(exceptionClasses = [PersistenceException::class])
    fun prepareEmail(user: User, url: String): SimpleMailMessage

    @Throws(exceptionClasses = [PersistenceException::class, VerificationTokenNotFoundException::class, VerificationTokenExpiredException::class])
    fun activateUser(token: String): User
}