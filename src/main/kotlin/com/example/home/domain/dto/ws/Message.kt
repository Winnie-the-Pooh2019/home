package com.example.home.domain.dto.ws

import com.example.home.domain.model.device.Action

data class Message(
//    val
    val content: String,
    val action: Action
)
