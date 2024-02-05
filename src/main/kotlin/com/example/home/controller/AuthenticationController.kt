package com.example.home.controller

import com.example.home.domain.dto.*
import com.example.home.domain.model.User
import com.example.home.exceptions.jpa.RoleNotFoundException
import com.example.home.exceptions.jpa.UserAlreadyExistsException
import com.example.home.service.UserService
import com.example.home.utils.HomeAppUtils
import jakarta.persistence.PersistenceException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthenticationController(
    @Autowired
    private val userService: UserService,
    @Autowired
    private val mailSender: JavaMailSender,
    @Autowired
    private val homeAppUtils: HomeAppUtils
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody signUpRequest: UserDto, request: HttpServletRequest): User {
        val user = userService.saveUser(signUpRequest)

        val email = userService.prepareEmail(user, homeAppUtils.getHostUrl(request))
        mailSender.send(email)

        return user
    }

    @GetMapping("/signup/confirm")
    fun confirmation(@RequestParam token: String, request: HttpServletRequest): Redirect {
        userService.activateUser(token)

        val url = homeAppUtils.getHostUrl(request)

        return Redirect(url = url)
    }

    @PostMapping("/signin")
    fun signIn(@RequestBody signInRequest: SignInRequest): ResponseEntity<SignInResponse> =
        ResponseEntity.ok(userService.signIn(signInRequest))

    @PostMapping("/refresh")
    fun refresh(@RequestBody refreshTokenRequest: RefreshTokenRequest): ResponseEntity<SignInResponse> =
        ResponseEntity.ok(userService.refreshToken(refreshTokenRequest))
}