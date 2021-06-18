package ua.com.alevel.data.model.dto;

import java.time.Instant;

public record OperationRecord(
        Long id,
        Long transactionAmount,
        Long balanceBefore,
        Long balanceAfter,
        Instant date,
        String type
) {
}
