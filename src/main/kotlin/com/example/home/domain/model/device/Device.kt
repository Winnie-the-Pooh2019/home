package com.example.home.domain.model.device

import com.example.home.domain.model.User
import jakarta.persistence.*
import java.util.HashSet
import java.util.UUID

@Entity
@Table(name = "devices")
data class Device(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Enumerated(EnumType.STRING)
    val type: DeviceType,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    var user: User? = null,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "devices_actions",
        joinColumns = [JoinColumn(name = "device_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "action_id", referencedColumnName = "id")])
    val actions: HashSet<Action> = HashSet()
)