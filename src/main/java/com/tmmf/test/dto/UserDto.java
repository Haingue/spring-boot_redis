package com.tmmf.test.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UserDto(
        String login,
        String password,
        List<ItemDto> items
) {
}
