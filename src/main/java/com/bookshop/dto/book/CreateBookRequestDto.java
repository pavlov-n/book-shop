package com.bookshop.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public record CreateBookRequestDto(
        @NotBlank @Size(max = 256)
        String title,
        @NotBlank @Size(max = 256)
        String author,
        @Pattern(regexp = "^\\d{3}-\\d{10}$")
        String isbn,
        @NotNull
        @Positive
        BigDecimal price,
        @Size(max = 1024)
        String description,
        @Size(max = 1024)
        String coverImage,
        List<Long> categoryIds){
}
