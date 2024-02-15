package com.example.home.service.impl

import com.example.home.domain.dto.DeviceDto
import com.example.home.domain.model.device.Device
import com.example.home.domain.model.device.DeviceType
import com.example.home.exceptions.ConvertException
import com.example.home.exceptions.UserNotFoundException
import com.example.home.repository.DeviceRepository
import com.example.home.repository.UserRepository
import com.example.home.service.DeviceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class DeviceServiceImpl(
    @Autowired
    private val deviceRepository: DeviceRepository,
    @Autowired
    private val userRepository: UserRepository
) : DeviceService {
    override fun registerDevice(deviceDto: DeviceDto): Device {
        val device = deviceDto.toDevice()

        return deviceRepository.save(device)
    }

    override fun addUser(userId: UUID, deviceDto: DeviceDto): Device {
        val user = userRepository.findById(userId)

        if (user.isEmpty)
            throw UserNotFoundException("No user with such id: $userId")

        val device = deviceDto.toDevice().apply { this.user = user.get() }

        return deviceRepository.save(device)
    }

    private fun DeviceDto.toDevice(): Device = try {
        Device(UUID.fromString(this.id), DeviceType.valueOf(this.type))
    } catch (e: IllegalArgumentException) {
        throw ConvertException("Device type or UUID has incorrect format")
    }
}