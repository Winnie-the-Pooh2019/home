package com.example.home.controller.websockets

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.security.MessageDigest
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Controller
class WebSocketsController {
    private val key = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11"

    @OptIn(ExperimentalEncodingApi::class)
    @GetMapping("/home/ws")
    fun connect(@RequestParam id: String, httpServletRequest: HttpServletRequest): ResponseEntity<*> {
        val s = httpServletRequest.getHeader("Sec-WebSocket-Key")

        var hash = "$s$key"
        val md5 = MessageDigest.getInstance("SHA-1")
        md5.update(hash.toByteArray(Charsets.UTF_8), 0, hash.length)

        val hashBytes = md5.digest()
        hash = Base64.encode(hashBytes, 0, hashBytes.size)

        println("shifr = $hash")

        val headers = HttpHeaders()
        headers["Upgrade"] = "websocket"
        headers["Connection"] = "upgrade"
        headers["Sec-WebSocket-Accept"] = hash
        headers["Sec-WebSocket-Extensions"] = "permessage-deflate;client_max_window_bits=15"
        headers["X-Content-Type-Options"] = "nosniff"
        headers["X-XSS-Protection"] = "0"
        headers["Cache-Control"] = "no-cache, no-store, max-age=0, must-revalidate"
        headers["Pragma"] = "no-cache"
        headers["Expires"] = "0"
        headers["X-Frame-Options"] = "DENY"

        val response = ResponseEntity<Any>(headers, HttpStatus.SWITCHING_PROTOCOLS)

        return response
    }
}