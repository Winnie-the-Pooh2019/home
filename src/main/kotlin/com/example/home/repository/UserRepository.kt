package com.example.home.repository

import com.example.home.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface UserRepository : JpaRepository<User, UUID> {
    fun findByEmail(email: String): Optional<User>

    fun findByUserName(username: String): Optional<User>

    fun findByUserNameOrEmail(username: String, email: String): Optional<User>
}