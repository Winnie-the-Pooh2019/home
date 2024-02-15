package com.example.home.configuration.websocket

import com.example.home.websockets.HomeWebsocketHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class HomeWebSocketConfiguration(
    @Autowired
    private val homeWebsocketHandler: HomeWebsocketHandler
) : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(homeWebsocketHandler, "/home/ws").setAllowedOrigins("*")
    }
}