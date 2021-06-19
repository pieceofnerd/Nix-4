package ua.com.alevel.data.model.dto;

import ua.com.alevel.data.model.entity.ExpenseCategory;

import java.util.Set;

public record OperationSaveExpense(
        Long accountId,
        Long transactionAmount,
        Set<ExpenseCategory> expenseCategories) {
}
