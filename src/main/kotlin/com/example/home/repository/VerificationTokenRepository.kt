package com.example.home.repository

import com.example.home.domain.model.VerificationToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface VerificationTokenRepository : JpaRepository<VerificationToken, UUID> {
    fun findVerificationTokenByUserId(userId: UUID): Optional<VerificationToken>

    fun findByToken(token: UUID): Optional<VerificationToken>
}