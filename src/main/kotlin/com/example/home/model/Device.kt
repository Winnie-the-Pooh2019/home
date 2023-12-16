package com.example.home.model

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.util.UUID

@Entity
@Table(name = "devices")
data class Device(
    @Id
    val id: UUID,

    @Column(nullable = false)
    val name: String,

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "type_id")
    @OnDelete(action = OnDeleteAction.RESTRICT)
    val type: Type,

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    val user: User
)
