package com.bookshop.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record CreateBookRequestDto(
        @NotBlank
        String title,
        @NotBlank
        String author,
        @Pattern(regexp = "^\\d{3}-\\d{10}$")
        String isbn,
        @NotNull
        @Positive
        BigDecimal price,
        String description,
        String coverImage
){
}
