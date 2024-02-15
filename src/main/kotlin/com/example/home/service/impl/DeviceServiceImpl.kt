package com.example.home.service.impl

import com.example.home.domain.dto.DeviceDto
import com.example.home.domain.dto.UserDto
import com.example.home.domain.model.device.Device
import com.example.home.domain.model.device.DeviceType
import com.example.home.exceptions.ConvertException
import com.example.home.repository.DeviceRepository
import com.example.home.service.DeviceService
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

class DeviceServiceImpl(
    @Autowired
    private val deviceRepository: DeviceRepository
) : DeviceService {
    override fun registerDevice(deviceDto: DeviceDto) {
        val device = try {
            Device(UUID.fromString(deviceDto.id), DeviceType.valueOf(deviceDto.type))
        } catch (e: IllegalArgumentException) {
            throw ConvertException("Device type or UUID has incorrect format")
        }

        deviceRepository.save(device)
    }

    override fun addUser(userDto: UserDto) {
        TODO("Not yet implemented")
    }
}