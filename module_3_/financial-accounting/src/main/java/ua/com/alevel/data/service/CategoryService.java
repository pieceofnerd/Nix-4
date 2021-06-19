package ua.com.alevel.data.service;

import ua.com.alevel.data.model.entity.ExpenseCategory;
import ua.com.alevel.data.model.entity.IncomeCategory;

import java.util.List;


public interface CategoryService {

     List<IncomeCategory> getAllIncomeCategories();

    List<ExpenseCategory> getAllExpenseCategories();

}
