package com.example.home.service.impl

import com.example.home.service.JwtService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import kotlin.jvm.Throws

@Service
class JwtServiceImpl(
    @Value("\${home.jwt.secret}")
    private val jwtSecret: String,
    @Value("\${home.jwt.expiration-ms}")
    private val jwtExpirationMs: Int,
    @Value("\${home.jwt.refresh-expiration}")
    private val refreshExpiration: Int
) : JwtService {
    override fun generateToken(userDetails: UserDetails): String {
        return Jwts.builder().setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + jwtExpirationMs))
            .signWith(getSignKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    override fun generateRefreshToken(extraClaims: Map<String, Any>, userDetails: UserDetails): String = Jwts
        .builder().setClaims(extraClaims).setSubject(userDetails.username)
        .setIssuedAt(Date(System.currentTimeMillis()))
        .setExpiration(Date(System.currentTimeMillis() + refreshExpiration))
        .signWith(getSignKey(), SignatureAlgorithm.HS256)
        .compact()

    override fun extractUserName(token: String): String = extractClaim(token, Claims::getSubject)

    override fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUserName(token)

        return (username == userDetails.username && !isTokenExpired(token))
    }

    private fun isTokenExpired(token: String): Boolean = extractClaim(token, Claims::getExpiration).before(Date())

    @Throws(exceptionClasses = [JwtException::class])
    private fun <T> extractClaim(token: String, claimsResolver: (claims: Claims) -> T): T =
        claimsResolver(extractAllClaims(token))

    private fun getSignKey(): Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))

    private fun extractAllClaims(token: String): Claims = Jwts
        .parserBuilder()
        .setSigningKey(getSignKey())
        .build()
        .parseClaimsJws(token)
        .body
}