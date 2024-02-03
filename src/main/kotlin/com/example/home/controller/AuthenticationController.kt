package com.example.home.controller

import com.example.home.domain.dto.*
import com.example.home.service.UserService
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
    private val mailSender: JavaMailSender
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody signUpRequest: UserDto, request: HttpServletRequest): ResponseEntity<*> {
        if (userService.userExists(signUpRequest))
            return ResponseEntity.badRequest().body(Message("Such user already exists"))

        val user = userService.saveUser(signUpRequest)
        val url = request.requestURL.toString().replace(request.servletPath, "")

        val email = userService.prepareEmail(user, url)
        mailSender.send(email)

        return ResponseEntity.ok(user)
    }

    @GetMapping("/signup/confirm")
    fun confirmation(@RequestParam token: String): ResponseEntity<*> =
        ResponseEntity.ok(userService.activateUser(token).id)

    @PostMapping("/signin")
    fun signIn(@RequestBody signInRequest: SignInRequest): ResponseEntity<SignInResponse> =
        ResponseEntity.ok(userService.signIn(signInRequest))

    @PostMapping("/refresh")
    fun refresh(@RequestBody refreshTokenRequest: RefreshTokenRequest): ResponseEntity<SignInResponse> =
        ResponseEntity.ok(userService.refreshToken(refreshTokenRequest))
}