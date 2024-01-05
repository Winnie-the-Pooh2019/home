package com.example.home.controller

import com.example.home.repository.UserRepository
import com.example.home.domain.model.User
import com.example.home.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class MainController(
    @Autowired
    private val userService: UserService
) {
    @GetMapping
    fun index() = "landing"

    @GetMapping("/users")
    fun showUsers(model: Model): String {
        val users = userService.findAllUsers()
        model.addAttribute("users", users)

        return "users"
    }
}