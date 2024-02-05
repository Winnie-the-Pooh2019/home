package com.example.home.exceptions.jpa

import jakarta.persistence.EntityNotFoundException

class UserNotFoundException(message: String?) : EntityNotFoundException(message)