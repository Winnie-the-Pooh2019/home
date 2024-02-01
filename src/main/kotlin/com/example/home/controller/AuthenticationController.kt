package com.example.home.controller

import com.example.home.domain.dto.SignUpRequest
import com.example.home.domain.dto.UserDto
import com.example.home.domain.model.User
import com.example.home.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthenticationController(
    @Autowired
    private val userService: UserService
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody signUpRequest: UserDto): ResponseEntity<User> =
        ResponseEntity.ok(userService.saveUser(signUpRequest))
}