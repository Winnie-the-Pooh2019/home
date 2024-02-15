package com.example.home.domain.model.device

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "devices_actions",
        joinColumns = [JoinColumn(name = "device_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "action_id", referencedColumnName = "id")])
    val actions: HashSet<Action> = HashSet()
)