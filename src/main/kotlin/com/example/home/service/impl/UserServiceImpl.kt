package com.example.home.service.impl

import com.example.home.domain.dto.RefreshTokenRequest
import com.example.home.domain.dto.SignInRequest
import com.example.home.domain.dto.SignInResponse
import com.example.home.domain.dto.UserDto
import com.example.home.domain.model.User
import com.example.home.repository.RoleRepository
import com.example.home.repository.UserRepository
import com.example.home.service.JwtService
import com.example.home.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import javax.naming.InvalidNameException

@Service
class UserServiceImpl(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val roleRepository: RoleRepository,
    @Autowired
    private val passwordEncoder: PasswordEncoder,
    @Autowired
    private val authenticationManager: AuthenticationManager,
    @Autowired
    private val jwtService: JwtService
) : UserService {

    override fun saveUser(userDto: UserDto): User {
        val user = User()
        val role = roleRepository.findByName("ROLE_USER").orElseThrow { Exception("No role ROLE_USER exists") }

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
                .orElseThrow { IllegalArgumentException("Invalid username or email") }

        val jwt = jwtService.generateToken(user)
        val refreshToken = jwtService.generateRefreshToken(HashMap(), user)

        val jwtAuthenticationResponse = SignInResponse(
            token = jwt,
            refreshToken = refreshToken
        )

        return jwtAuthenticationResponse
    }

    override fun refreshToken(@RequestBody refreshTokenRequest: RefreshTokenRequest): SignInResponse? {
        val username = jwtService.extractUserName(refreshTokenRequest.refreshToken)
        val user = userRepository.findByUserName(username).orElseThrow { InvalidNameException("No user found") }

        if (jwtService.isTokenValid(refreshTokenRequest.refreshToken, user)) {
            val jwt = jwtService.generateToken(user)
            val refreshToken = jwtService.generateRefreshToken(HashMap(), user)

            val signInResponse = SignInResponse(
                token = jwt,
                refreshToken = refreshToken
            )

            return signInResponse
        }
        // todo throw exception on token expiration
        return null
    }

    private fun isEmail(usernameOrEmail: String) = usernameOrEmail.contains('@')

    override fun existsUserByEmail(email: String): Boolean = userRepository.findByEmail(email).isPresent
    override fun existsUserByUserName(username: String): Boolean = userRepository.findByUserName(username).isPresent

    override fun findAllUsers(): List<UserDto> = userRepository.findAll().map { UserDto(it.email, it.passWord) }
}