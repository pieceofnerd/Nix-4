package ua.com.alevel.data.model.dto;

import ua.com.alevel.data.model.entity.Operation;

import java.util.List;

public record QuittanceRecord(
        List<OperationRecord> operationList,
        Long income,
        Long balanceInHand
) {
}
