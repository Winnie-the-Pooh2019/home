package com.example.home.repository

import com.example.home.domain.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface RoleRepository : JpaRepository<Role, Int> {
    fun findByName(name: String): Optional<Role>
}