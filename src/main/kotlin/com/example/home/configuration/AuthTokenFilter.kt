package com.example.home.configuration

import com.example.home.service.HomeUserDetailsService
import com.example.home.utils.JwtUtils
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

class AuthTokenFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var jwtUtils: JwtUtils

    @Autowired
    private lateinit var userDetailsService: HomeUserDetailsService

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt = parseJwt(request)

        if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
            val username = jwtUtils.getUsernameFromJwt(jwt)

            val userDetails = userDetailsService.loadUserByUsername(username)
            val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

    private fun parseJwt(request: HttpServletRequest): String? {
        val headerAuth = request.getHeader("Authorization")

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer "))
            return headerAuth.substring(7, headerAuth.length)

        return null
    }
}