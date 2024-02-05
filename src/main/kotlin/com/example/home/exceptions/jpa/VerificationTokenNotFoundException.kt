package com.example.home.exceptions.jpa

import jakarta.persistence.EntityNotFoundException

class VerificationTokenNotFoundException(message: String) : EntityNotFoundException(message)