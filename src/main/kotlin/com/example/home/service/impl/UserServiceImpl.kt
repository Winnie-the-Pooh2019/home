package com.example.home.service.impl

import com.example.home.domain.dto.RefreshTokenRequest
import com.example.home.domain.dto.SignInRequest
import com.example.home.domain.dto.SignInResponse
import com.example.home.domain.dto.UserDto
import com.example.home.domain.model.User
import com.example.home.domain.model.VerificationToken
import com.example.home.exceptions.TokenExpiredException
import com.example.home.exceptions.jpa.RoleNotFoundException
import com.example.home.exceptions.jpa.UserAlreadyExistsException
import com.example.home.exceptions.jpa.UserNotFoundException
import com.example.home.exceptions.jpa.VerificationTokenNotFoundException
import com.example.home.repository.RoleRepository
import com.example.home.repository.UserRepository
import com.example.home.repository.VerificationTokenRepository
import com.example.home.service.JwtService
import com.example.home.service.UserService
import jakarta.persistence.PersistenceException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserServiceImpl(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val roleRepository: RoleRepository,
    @Autowired
    private val verificationTokenRepository: VerificationTokenRepository,
    @Autowired
    private val passwordEncoder: PasswordEncoder,
    @Autowired
    private val authenticationManager: AuthenticationManager,
    @Autowired
    private val jwtService: JwtService
) : UserService {

    @Transactional
    override fun saveUser(userDto: UserDto): User {
        if (userExists(userDto))
            throw UserAlreadyExistsException("Username or email is already taken")

        val user = User()
        val role = roleRepository.findByName("ROLE_USER")
            .orElseThrow { RoleNotFoundException("No role ROLE_USER exists") }

        user
            .addUsername(userDto.username)
            .addEmail(userDto.email)
            .addPassword(passwordEncoder.encode(userDto.password))
            .addRoles(setOf(role))

        return userRepository.save(user)
    }

    override fun signIn(signInRequest: SignInRequest): SignInResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                signInRequest.usernameOrEmail,
                signInRequest.password
            )
        )

        val user =
            (if (isEmail(signInRequest.usernameOrEmail)) userRepository.findByEmail(signInRequest.usernameOrEmail)
            else userRepository.findByUserName(signInRequest.usernameOrEmail))
                .orElseThrow { UserNotFoundException("Invalid username or email") }

        val jwt = jwtService.generateToken(user)
        val refreshToken = jwtService.generateRefreshToken(HashMap(), user)

        return SignInResponse(
            token = jwt,
            refreshToken = refreshToken
        )
    }

    override fun refreshToken(refreshTokenRequest: RefreshTokenRequest): SignInResponse {
        val username = jwtService.extractUserName(refreshTokenRequest.refreshToken)
        val user = userRepository.findByUserName(username)
            .orElseThrow { UserNotFoundException("No user found") }

        if (!jwtService.isTokenValid(refreshTokenRequest.refreshToken, user))
            throw TokenExpiredException("Token expired")

        val jwt = jwtService.generateToken(user)
        val refreshToken = jwtService.generateRefreshToken(HashMap(), user)

        return SignInResponse(
            token = jwt,
            refreshToken = refreshToken
        )
    }

    override fun prepareEmail(user: User, url: String): SimpleMailMessage {
        val verificationToken = verificationTokenRepository.save(VerificationToken(user = user))

        val email = SimpleMailMessage()

        email.setTo(user.email)
        email.subject = "Registration confirmation"
        email.text = "$url/api/auth/signup/confirm?token=${verificationToken.token}"

        return email
    }

    @Transactional
    @Throws(exceptionClasses = [VerificationTokenNotFoundException::class, PersistenceException::class])
    fun findUserByVerificationToken(token: String): User {
        val verificationToken = verificationTokenRepository.findById(UUID.fromString(token))

        if (verificationToken.isEmpty)
            throw VerificationTokenNotFoundException("You have been already completed registration")

        val user = verificationToken.get().user
        verificationTokenRepository.delete(verificationToken.get())

        return user
    }

    @Transactional
    override fun activateUser(token: String): User {
        val user = findUserByVerificationToken(token)
        user.enabled = true

        return userRepository.save(user)
    }

    private fun isEmail(usernameOrEmail: String) = usernameOrEmail.contains('@')

    fun userExists(userDto: UserDto): Boolean =
        userRepository.findByUserNameOrEmail(userDto.username, userDto.email).isPresent
}