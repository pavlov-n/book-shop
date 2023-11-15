package com.bookshop.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(
        @NotNull @Email
        String email,
        @NotBlank @Size(min = 6, max = 32)
        String password) {
}
