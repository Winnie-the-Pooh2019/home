package com.example.home.repository

import com.example.home.domain.model.device.Action
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ActionRepository : JpaRepository<Action, UUID> {
}