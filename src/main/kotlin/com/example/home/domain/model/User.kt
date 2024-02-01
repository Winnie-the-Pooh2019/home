package com.example.home.domain.model

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.validation.constraints.Email

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(name = "username", unique = true, nullable = false)
    var userName: String = "",

    @Email
    @Column(unique = true, nullable = false)
    var email: String = "",

    @Column(name = "password")
    var passWord: String = "",

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")])
    var roles: Set<Role> = emptySet()
) : UserDetails {
    fun addUsername(username: String) = apply { this.userName = username }

    fun addEmail(email: String) = apply { this.email = email }

    fun addPassword(password: String) = apply { this.passWord = password }

    fun addRoles(roles: Set<Role>) = apply { this.roles = roles }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = roles.toMutableList()

    override fun getPassword(): String = passWord

    override fun getUsername(): String = userName

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
