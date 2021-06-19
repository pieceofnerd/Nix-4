package ua.com.alevel.data.service;

import ua.com.alevel.data.exception.FinancialAccountingExceptionDataNotFound;
import ua.com.alevel.data.model.dto.OperationSaveExpense;
import ua.com.alevel.data.model.dto.OperationSaveIncome;

public interface OperationService {

    void createOperationIncome(OperationSaveIncome operationSaveIncome) throws FinancialAccountingExceptionDataNotFound;

    void createOperationExpense(OperationSaveExpense operationSaveExpense) throws FinancialAccountingExceptionDataNotFound;


}
