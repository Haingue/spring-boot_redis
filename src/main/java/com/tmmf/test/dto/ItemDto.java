package com.tmmf.test.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ItemDto(
        long id,
        String name,
        String description,
        int leadTime,
        double weight,
        UUID owner,
        LocalDateTime deliveryDate
) {
}
