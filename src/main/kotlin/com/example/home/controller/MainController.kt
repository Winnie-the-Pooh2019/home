package com.example.home.controller

import com.example.home.UserRepository
import com.example.home.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class MainController(
    @Autowired
    private val userRepository: UserRepository
) {
    @GetMapping
    fun index() = "hello";

    @PostMapping("add")
    fun addUser(user: User): String {
        userRepository.save(user)

        return "congrats";
    }
}