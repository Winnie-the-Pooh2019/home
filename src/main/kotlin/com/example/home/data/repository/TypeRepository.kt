package com.example.home.data.repository

import com.example.home.model.Type
import org.springframework.data.jpa.repository.JpaRepository

interface TypeRepository : JpaRepository<Type, Int> {
}