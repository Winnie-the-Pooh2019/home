package com.example.home.service

import com.example.home.domain.dto.DeviceDto
import com.example.home.domain.dto.UserDto
import com.example.home.exceptions.ConvertException
import jakarta.persistence.PersistenceException
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
interface DeviceService {
    @Throws(exceptionClasses = [ConvertException::class, PersistenceException::class])
    fun registerDevice(deviceDto: DeviceDto)

    fun addUser(userDto: UserDto)
}