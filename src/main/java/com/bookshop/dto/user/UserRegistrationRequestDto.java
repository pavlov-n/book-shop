package com.bookshop.dto.user;

import com.bookshop.validation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@FieldMatch(message = "Password don`t match",
        field = "password",
        fieldMatch = "repeatPassword")
public record UserRegistrationRequestDto(
        @NotNull @Email
        String email,
        @NotNull @Size(min = 4, max = 32)
        @Pattern(
                regexp = "^[a-zA-Z0-9]{6,}$",
                message = "Password must contain at least one digit and letter"
        )
        String password,
        String verifyPassword,

        @NotNull @Size(min = 2, max = 32)
        String firstName,

        @NotNull @Size(min = 2, max = 32)
        String lastName,

        @NotNull @Size(max = 128)
        String shippingAddress) {
}
