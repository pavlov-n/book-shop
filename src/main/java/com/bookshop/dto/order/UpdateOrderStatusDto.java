package com.bookshop.dto.order;

import com.bookshop.model.Status;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderStatusDto(
        @NotNull
        Status status
) {
}
