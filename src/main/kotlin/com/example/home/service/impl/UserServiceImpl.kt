package com.example.home.service.impl

import com.example.home.domain.dto.RefreshTokenRequest
import com.example.home.domain.dto.SignInRequest
import com.example.home.domain.dto.SignInResponse
import com.example.home.domain.dto.UserDto
import com.example.home.domain.model.User
import com.example.home.domain.model.VerificationToken
import com.example.home.exceptions.RoleNotExistsException
import com.example.home.exceptions.UserActivationException
import com.example.home.exceptions.UserAlreadyExistsException
import com.example.home.exceptions.VerificationTokenNotExistException
import com.example.home.repository.RoleRepository
import com.example.home.repository.UserRepository
import com.example.home.repository.VerificationTokenRepository
import com.example.home.service.JwtService
import com.example.home.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestBody
import java.util.*
import javax.naming.InvalidNameException

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
            .orElseThrow { RoleNotExistsException("No role ROLE_USER exists") }

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

        return SignInResponse(
            token = jwt,
            refreshToken = refreshToken
        )
    }

    override fun refreshToken(@RequestBody refreshTokenRequest: RefreshTokenRequest): SignInResponse? {
        val username = jwtService.extractUserName(refreshTokenRequest.refreshToken)
        val user = userRepository.findByUserName(username).orElseThrow { InvalidNameException("No user found") }

        if (jwtService.isTokenValid(refreshTokenRequest.refreshToken, user)) {
            val jwt = jwtService.generateToken(user)
            val refreshToken = jwtService.generateRefreshToken(HashMap(), user)

            return SignInResponse(
                token = jwt,
                refreshToken = refreshToken
            )
        }
        // todo throw exception on token expiration
        return null
    }

    override fun prepareEmail(user: User, url: String): SimpleMailMessage {
        val email = SimpleMailMessage()
        val verificationToken = VerificationToken(
            token = UUID.randomUUID(),
            user = user
        )

        verificationTokenRepository.save(verificationToken)

        email.setTo(user.email)
        email.subject = "Registration confirmation"
        email.text = "$url/api/auth/signup/confirm?token=${verificationToken.token}"

        return email
    }

    private fun findUserByVerificationToken(token: String): User {
        val verificationToken = verificationTokenRepository.findById(UUID.fromString(token))

        println("JFKJSAL;FJSAKLFJ;SALFJK;SA;KLFJSA;LFJSA;LFJSAKLDFJ;LSDFJKLSA;DFJKL ${verificationToken.get()}")

        if (verificationToken.isEmpty)
            throw VerificationTokenNotExistException("Code you sent doesnt exists")

        val user = verificationToken.get().user
        verificationTokenRepository.delete(verificationToken.get())

        return user
    }

    override fun activateUser(token: String) = try {
        val user = findUserByVerificationToken(token)
        user.enabled = true

        userRepository.save(user)
    } catch (e: Exception) {
        throw UserActivationException()
    }

    private fun isEmail(usernameOrEmail: String) = usernameOrEmail.contains('@')

    override fun existsUserByEmail(email: String): Boolean = userRepository.findByEmail(email).isPresent

    override fun existsUserByUserName(username: String): Boolean = userRepository.findByUserName(username).isPresent

    override fun userExists(userDto: UserDto): Boolean =
        userRepository.findByUserNameOrEmail(userDto.username, userDto.email).isPresent

    override fun findAllUsers(): List<UserDto> = userRepository.findAll().map { UserDto(it.email, it.passWord) }
}