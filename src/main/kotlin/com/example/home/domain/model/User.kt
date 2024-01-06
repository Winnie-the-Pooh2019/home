package com.example.home.domain.model

import jakarta.persistence.*
import java.util.*
import javax.validation.constraints.Email

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(unique = true, nullable = false)
    var username: String = "",

    @Email
    @Column(unique = true, nullable = false)
    var email: String = "",

    var password: String = "",

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")])
    var roles: Set<Role> = emptySet()
) {
    fun addUsername(username: String) = apply { this.username = username }

    fun addEmail(email: String) = apply { this.email = email }

    fun addPassword(password: String) = apply { this.password = password }

    fun addRoles(roles: Set<Role>) = apply { this.roles = roles }
}
