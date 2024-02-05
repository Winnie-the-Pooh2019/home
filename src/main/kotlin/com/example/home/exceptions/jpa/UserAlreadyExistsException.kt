package com.example.home.exceptions.jpa

import jakarta.persistence.EntityExistsException

class UserAlreadyExistsException(message: String) : EntityExistsException(message)