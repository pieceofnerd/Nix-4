package ua.com.alevel.data.model.dto;

import ua.com.alevel.data.model.entity.Client;

public record AccountRecord(
        Long id,
        Long balance,
        Client client
) {
}
