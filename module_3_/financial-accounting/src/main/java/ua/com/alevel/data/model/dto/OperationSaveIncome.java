package ua.com.alevel.data.model.dto;

import ua.com.alevel.data.model.entity.IncomeCategory;

import java.util.Set;

public record OperationSaveIncome(
        Long accountId,
        Long transactionAmount,
        Set<IncomeCategory> incomeCategories
) {
}
