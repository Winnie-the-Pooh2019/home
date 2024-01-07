package com.example.home.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/test")
class TestController {

    @GetMapping("/all")
    fun all() = "Public content"

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    fun user() = "User content"

    @GetMapping("/admin")
    @PreAuthorize("hasRole('USER')")
    fun admin() = "Admin only"
}