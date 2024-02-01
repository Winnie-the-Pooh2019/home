package com.example.home.domain.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID


class HomeUserDetails(
    var id: String = "",
    var userName: String = "",
    var email: String = "",
    var passWord: String = "",
    val roles: MutableList<Role> = mutableListOf()
) : UserDetails {

    fun addId(id: UUID) = apply { this.id = id.toString() }

    fun addUsername(username: String) = apply { this.userName = username }

    fun addEmail(email: String) = apply { this.email = email }

    fun addPassword(password: String) = apply { this.passWord = password }

    fun addRoles(roles: Set<Role>) = apply { this.roles.addAll(roles.map { it  }) }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = roles

    override fun getPassword(): String = passWord

    override fun getUsername(): String = userName

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}