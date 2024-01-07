package com.example.home.domain.payload

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class SignupPayload(
    @NotBlank
    @Size(min = 5, max = 21)
    val username: String = "",

    @NotBlank
    @Email
    val email: String = "",

    @NotBlank
    @Size(min = 8, max = 50)
    val password: String = ""
)
