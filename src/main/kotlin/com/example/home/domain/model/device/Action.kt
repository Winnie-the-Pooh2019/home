package com.example.home.domain.model.device

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "actions")
data class Action(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private val id: UUID? = null,

    @Column(unique = true, nullable = false)
    private val name: String
)
