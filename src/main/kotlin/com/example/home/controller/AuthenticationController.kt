package com.example.home.controller

import com.example.home.domain.dto.*
import com.example.home.domain.dto.auth.RefreshTokenRequest
import com.example.home.domain.dto.auth.SignInRequest
import com.example.home.domain.dto.auth.SignInResponse
import com.example.home.domain.dto.util.Redirect
import com.example.home.service.UserService
import com.example.home.utils.HomeAppUtils
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
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
    fun signUp(@RequestBody signUpRequest: UserDto, request: HttpServletRequest): Redirect {
        val user = userService.saveUser(signUpRequest, request)

        val url = homeAppUtils.getHostUrl(request)
        val email = userService.prepareEmail(user, url)
        mailSender.send(email)

        return Redirect("$url/api/auth/signin")
    }

    @GetMapping("/signup/confirm")
    fun confirmation(@RequestParam token: String, request: HttpServletRequest): Redirect {
        userService.activateUser(token)

        val url = homeAppUtils.getHostUrl(request)

        return Redirect(url = url)
    }

    @PostMapping("/signin")
    fun signIn(@RequestBody signInRequest: SignInRequest): SignInResponse = userService.signIn(signInRequest)

    @PostMapping("/refresh")
    fun refresh(@RequestBody refreshTokenRequest: RefreshTokenRequest): SignInResponse =
        userService.refreshToken(refreshTokenRequest)
}