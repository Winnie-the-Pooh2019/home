package com.example.home.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin")
class AdminController {
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun test(): ResponseEntity<String> = ResponseEntity.ok("Hello admin")
}