package com.example.home.controller

import com.example.home.data.repository.UserRepository
import com.example.home.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
@RequestMapping("/")
class MainController(
    @Autowired
    private val userRepository: UserRepository
) {
    @GetMapping
    fun index() = "hello"

    @PostMapping("add")
    fun addUser(user: User): String {
        userRepository.save(user)

        return "congrats"
    }

    @RequestMapping(method = [RequestMethod.HEAD])
    fun checkStatus(): ResponseEntity<HttpStatus> = ResponseEntity.ok(HttpStatus.OK)
}