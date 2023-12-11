package com.example.home.model

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.validation.constraints.Email

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    val id: UUID,

    @Email
    @Column(unique = true, nullable = false)
    val email: String,

    val password: String
)
