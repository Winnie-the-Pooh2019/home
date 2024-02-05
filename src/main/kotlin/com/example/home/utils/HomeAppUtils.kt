package com.example.home.utils

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component

@Component
class HomeAppUtils {
    fun getHostUrl(request: HttpServletRequest): String = request
        .requestURL
        .toString()
        .replace(request.servletPath, "")
}