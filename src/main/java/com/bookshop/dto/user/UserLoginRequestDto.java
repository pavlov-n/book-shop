package com.bookshop.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserLoginRequestDto(
        @NotNull @Email
        String email,
        @NotBlank
        String password) {

}
