package com.example.home.controller

import com.example.home.domain.model.User
import com.example.home.domain.payload.JwtResponse
import com.example.home.domain.payload.LoginPayload
import com.example.home.domain.payload.MessageResponse
import com.example.home.domain.payload.SignupPayload
import com.example.home.repository.RoleRepository
import com.example.home.repository.UserRepository
import com.example.home.service.UserService
import com.example.home.utils.JwtUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import kotlin.jvm.optionals.toSet

typealias UserDetails = org.springframework.security.core.userdetails.User

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
class JwtAuthenticationController(
    @Autowired
    private val authenticationManager: AuthenticationManager,

    @Autowired
    private val userRepository: UserRepository,

    @Autowired
    private val userService: UserService,

    @Autowired
    private val roleRepository: RoleRepository,

    @Autowired
    private val passwordEncoder: PasswordEncoder,

    @Autowired
    private val jwtUtils: JwtUtils
) {

    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody loginPayload: LoginPayload): ResponseEntity<*> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginPayload.username,
                loginPayload.password
            )
        )

        SecurityContextHolder.getContext().authentication = authentication

        val jwt = jwtUtils.generateJwtToken(authentication)

        val userDetails = authentication.principal as UserDetails
        val roles = userDetails.authorities.map { it.authority }

        return ResponseEntity.ok(JwtResponse(token = jwt, username = userDetails.username, roles = roles))
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody signupPayload: SignupPayload): ResponseEntity<*> {
        if (userService.existsUserByUsername(signupPayload.username))
            return ResponseEntity.badRequest().body(MessageResponse("Username is taken"))

        if (userService.existsUserByEmail(signupPayload.email))
            return ResponseEntity.badRequest().body(MessageResponse("Email is taken"))

        val user = User(
            username = signupPayload.username,
            email = signupPayload.email,
            password = passwordEncoder.encode(signupPayload.password)
        )

        val roles = roleRepository.findByName("ROLE_USER").toSet()

        user.addRoles(roles)
        userRepository.save(user)

        return ResponseEntity.ok(MessageResponse("You registered successfully"))
    }
}