package com.bookshop.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDto(
        @NotBlank
        @Size(max = 32)
        String name,
        @Size(max = 256)
        String description
) {}
