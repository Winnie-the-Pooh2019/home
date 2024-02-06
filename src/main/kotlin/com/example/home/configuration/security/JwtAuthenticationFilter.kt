package com.example.home.configuration.security

import com.example.home.service.HomeUserDetailsService
import com.example.home.service.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    @Autowired
    private val jwtService: JwtService,
    @Autowired
    private val homeUserDetailsService: HomeUserDetailsService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        if (authHeader.isNullOrBlank() || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val jwt = authHeader.substring(7)
        val userName = jwtService.extractUserName(jwt)

        if (userName.isNotEmpty() && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = homeUserDetailsService.loadUserByUserNameOrEmail(userName)

            if (jwtService.isTokenValid(jwt, userDetails)) {
                val securityContext = SecurityContextHolder.createEmptyContext()

                val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)

                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

                securityContext.authentication = authentication
                SecurityContextHolder.setContext(securityContext)
            }
        }

        filterChain.doFilter(request, response)
    }
}