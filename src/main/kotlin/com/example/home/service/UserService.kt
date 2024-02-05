package com.example.home.service

import com.example.home.domain.dto.RefreshTokenRequest
import com.example.home.domain.dto.SignInRequest
import com.example.home.domain.dto.SignInResponse
import com.example.home.domain.dto.UserDto
import com.example.home.domain.model.User
import com.example.home.exceptions.UserActivationException
import com.example.home.exceptions.jpa.RoleNotFoundException
import com.example.home.exceptions.jpa.UserAlreadyExistsException
import com.example.home.exceptions.jpa.UserNotFoundException
import jakarta.persistence.PersistenceException
import jakarta.servlet.http.HttpServletRequest
import org.apache.tomcat.websocket.AuthenticationException
import org.springframework.mail.SimpleMailMessage
import kotlin.jvm.Throws

interface UserService {
    @Throws(exceptionClasses = [UserAlreadyExistsException::class, RoleNotFoundException::class, PersistenceException::class])
    fun saveUser(userDto: UserDto): User

    @Throws(exceptionClasses = [UserNotFoundException::class, AuthenticationException::class])
    fun signIn(signInRequest: SignInRequest): SignInResponse

    fun refreshToken(refreshTokenRequest: RefreshTokenRequest): SignInResponse?

    @Throws(exceptionClasses = [PersistenceException::class])
    fun prepareEmail(user: User, url: String): SimpleMailMessage

    @Throws(exceptionClasses = [UserActivationException::class])
    fun activateUser(token: String): User

    fun existsUserByEmail(email: String): Boolean

    fun existsUserByUserName(username: String): Boolean

    fun userExists(userDto: UserDto): Boolean

    fun findAllUsers(): List<UserDto>
}