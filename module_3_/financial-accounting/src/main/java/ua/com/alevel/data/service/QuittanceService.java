package ua.com.alevel.data.service;

import ua.com.alevel.data.model.dto.OperationRecord;
import ua.com.alevel.data.model.dto.QuittanceRecord;
import ua.com.alevel.data.model.entity.Operation;

import java.time.Instant;
import java.util.List;

public interface QuittanceService {

    List<OperationRecord> getOperationsByTimePeriod(Long accountId, Instant timeFrom, Instant timeTo);

    Long generalIncome(List<OperationRecord> operations);

    Long balanceInHand(List<OperationRecord> operations);

    QuittanceRecord generateQuittance(List<OperationRecord> operations, Long income, Long balanceInHand);


}
