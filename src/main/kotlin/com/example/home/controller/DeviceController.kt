package com.example.home.controller

import com.example.home.domain.dto.DeviceDto
import com.example.home.service.DeviceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/device")
class DeviceController(
    @Autowired
    private val deviceService: DeviceService
) {

    @PostMapping("/add")
    fun addDevice(deviceDto: DeviceDto) = deviceService.registerDevice(deviceDto)

    @PostMapping("/add/user")
    fun addUserToDevice(userId: UUID, deviceDto: DeviceDto) = deviceService.addUser(userId, deviceDto)
}