package com.example.home.controller
//
//import com.example.home.domain.dto.UserDto
//import com.example.home.service.UserService
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.stereotype.Controller
//import org.springframework.ui.Model
//import org.springframework.validation.BindingResult
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.ModelAttribute
//import org.springframework.web.bind.annotation.PostMapping
//import javax.validation.Valid
//
//@Controller
//class AuthenticationController(
//    @Autowired
//    private val userService: UserService
//) {
//    @GetMapping("/register")
//    fun registrationForm(model: Model): String {
//        val emptyDto = UserDto()
//        model.addAttribute("user", emptyDto)
//
//        return "register"
//    }
//
//    @PostMapping("/register")
//    fun registration(@Valid @ModelAttribute("user") userDto: UserDto, result: BindingResult, model: Model): String {
//        if (userService.existsUserByEmail(userDto.email))
//            result.rejectValue("email", "EmailExists", "Such email is already in use")
//
//        if (result.hasErrors()) {
//            model.addAttribute("user", userDto)
//            return "/register"
//        }
//
//        userService.saveUser(userDto)
//        return "redirect:register?success"
//    }
//
//    @GetMapping("/signin")
//    fun loginPage() = "signin"
//}