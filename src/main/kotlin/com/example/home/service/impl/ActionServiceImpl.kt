package com.example.home.service.impl

import com.example.home.domain.model.device.Action
import com.example.home.domain.model.device.ActionEnum
import com.example.home.service.ActionService

class ActionServiceImpl : ActionService {

    fun proceed(action: Action) = when (ActionEnum.valueOf(action.name)) {
        ActionEnum.TURN_ON -> turnOn()
        ActionEnum.TURN_OFF -> turnOff()
    }

    fun turnOn() {
        //todo
    }

    fun turnOff() {
        //todo
    }
}