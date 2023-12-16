package com.example.home.model

import jakarta.persistence.*
import java.util.*
import javax.validation.constraints.Email

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Email
    @Column(unique = true, nullable = false)
    val email: String,

    val password: String,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    val devices: List<Device>
)
