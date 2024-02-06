package com.example.home.service

import com.example.home.domain.dto.RefreshTokenRequest
import com.example.home.domain.dto.SignInRequest
import com.example.home.domain.dto.SignInResponse
import com.example.home.domain.dto.UserDto
import com.example.home.domain.model.User
import com.example.home.exceptions.*
import io.jsonwebtoken.JwtException
import jakarta.persistence.PersistenceException
import org.springframework.mail.SimpleMailMessage
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException

interface UserService {
    @Throws(exceptionClasses = [UserAlreadyExistsException::class, RoleNotFoundException::class, PersistenceException::class])
    fun saveUser(userDto: UserDto): User

    @Throws(exceptionClasses = [UserNotFoundException::class, DisabledException::class, LockedException::class, PersistenceException::class])
    fun signIn(signInRequest: SignInRequest): SignInResponse

    @Throws(exceptionClasses = [JwtException::class, TokenExpiredException::class, UserNotFoundException::class, PersistenceException::class])
    fun refreshToken(refreshTokenRequest: RefreshTokenRequest): SignInResponse

    @Throws(exceptionClasses = [PersistenceException::class])
    fun prepareEmail(user: User, url: String): SimpleMailMessage

    @Throws(exceptionClasses = [PersistenceException::class, VerificationTokenNotFoundException::class, VerificationTokenExpiredException::class])
    fun activateUser(token: String): User
}