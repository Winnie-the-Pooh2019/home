package com.example.home

import com.example.home.domain.model.User
import com.example.home.repository.RoleRepository
import com.example.home.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication
class HomeApplication(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val roleRepository: RoleRepository
) : CommandLineRunner {
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    override fun run(vararg args: String?) {
        val adminUser = userRepository.findByUserName("admin")

        if (adminUser.isEmpty) {
            val roleAdmin = roleRepository.findByName("ROLE_ADMIN")

            if (roleAdmin.isEmpty)
                throw Exception("No role admin found")

            val userAdmin = User(
                userName = "admin",
                email = "admin@admin.com",
                enabled = false,
                passWord = passwordEncoder().encode("admin"),
                roles = setOf(roleAdmin.get())
            )

            userRepository.save(userAdmin)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<HomeApplication>(*args)
}
