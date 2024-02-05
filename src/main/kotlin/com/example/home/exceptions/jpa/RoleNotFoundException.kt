package com.example.home.exceptions.jpa

import jakarta.persistence.EntityNotFoundException

class RoleNotFoundException(message: String) : EntityNotFoundException(message)