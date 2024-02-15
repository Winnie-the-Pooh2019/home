package com.example.home.service

import com.example.home.domain.dto.DeviceDto
import com.example.home.domain.model.device.Device
import com.example.home.exceptions.ConvertException
import jakarta.persistence.PersistenceException
import java.util.*

interface DeviceService {
    @Throws(exceptionClasses = [ConvertException::class, PersistenceException::class])
    fun registerDevice(deviceDto: DeviceDto): Device

    fun addUser(userId: UUID, deviceDto: DeviceDto): Device
}