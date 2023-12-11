package com.example.home.model

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
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

    val password: String
)
