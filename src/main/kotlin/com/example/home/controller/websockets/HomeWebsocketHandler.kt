package com.example.home.controller.websockets

import com.example.home.domain.dto.ws.Message
import com.example.home.repository.UserRepository
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class HomeWebsocketHandler(
    @Autowired
    private val userRepository: UserRepository
) : TextWebSocketHandler() {

    private val gson = Gson()
    private val sessions = HashMap<String, WebSocketSession>()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        println("connection opened")
        sessions[session.id] = session

        super.afterConnectionEstablished(session)
    }

    override fun handleMessage(session: WebSocketSession, message: WebSocketMessage<*>) {
        val payload = gson.fromJson((message.payload as String), Message::class.java)

        println("device = $payload")
        session.sendMessage(TextMessage("message assepted"))

        println("serialized session = ${session}")

        super.handleMessage(session, message)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        println("connection closed")
        sessions.remove(session.id)

        super.afterConnectionClosed(session, status)
    }
}