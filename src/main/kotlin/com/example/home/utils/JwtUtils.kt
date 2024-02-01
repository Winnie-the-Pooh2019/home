package com.example.home.utils

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtUtils(
    @Value("\${home.jwt.secret}")
    private val jwtSecret: String,
    @Value("\${home.jwt.expiration-ms}")
    private val jwtExpirationMs: Int
) {
    private val logger: KLogger = KotlinLogging.logger { }

    fun generateJwtToken(authentication: Authentication): String {
        val userPrincipal = authentication.principal as User

        return Jwts.builder().setSubject(userPrincipal.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + jwtExpirationMs))
            .signWith(key(), SignatureAlgorithm.HS256)
            .compact()
    }

    // TODO remove deprecated statements
//    fun getUsernameFromJwt(token: String): String = Jwts.parser().verifyWith(key()).build()
//        .parseClaimsJwt(token).body.subject;

//    fun validateJwtToken(authToken: String): Boolean {
//        try {
//            Jwts.parser().verifyWith(key()).build().parse(authToken)
//            return true
//        } catch (e: MalformedJwtException) {
//            logger.error { "Invalid JWT token: ${e.message}" }
//        } catch (e: ExpiredJwtException) {
//            logger.error { "JWT token is expired: {e.message}" }
//        } catch (e: UnsupportedJwtException) {
//            logger.error { "JWT token is unsupported: {e.message}" }
//        } catch (e: IllegalArgumentException) {
//            logger.error { "JWT claims string is empty: {e.message}" }
//        }
//
//        return false
//    }

    private fun key() = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))
}