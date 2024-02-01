package com.example.home.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController {

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    fun test(): ResponseEntity<String> = ResponseEntity.ok("Hello user")
}