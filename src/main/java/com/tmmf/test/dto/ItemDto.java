package com.tmmf.test.dto;

import java.time.LocalDateTime;

public record ItemDto(
        long id,
        String name,
        String description,
        int leadTime,
        double weight,
        LocalDateTime deliveryDate
) {
}
