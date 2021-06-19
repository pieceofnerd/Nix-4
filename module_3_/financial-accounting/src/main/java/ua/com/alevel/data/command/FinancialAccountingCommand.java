package ua.com.alevel.data.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.data.exception.FinancialAccountingExceptionDataNotFound;
import ua.com.alevel.data.model.dto.OperationRecord;
import ua.com.alevel.data.model.dto.OperationSaveExpense;
import ua.com.alevel.data.model.dto.OperationSaveIncome;
import ua.com.alevel.data.model.dto.QuittanceRecord;
import ua.com.alevel.data.model.entity.ExpenseCategory;
import ua.com.alevel.data.model.entity.IncomeCategory;
import ua.com.alevel.data.service.ClientService;
import ua.com.alevel.data.service.CreatingOperationService;
import ua.com.alevel.data.service.QuittanceService;
import ua.com.alevel.data.service.jdbc.JDBCFinancialService;
import ua.com.alevel.data.util.CSVParser;
import ua.com.alevel.data.view.FinancialAccountingView;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class FinancialAccountingCommand {

    private final FinancialAccountingView view;

    private static final Logger logger = LoggerFactory.getLogger(FinancialAccountingCommand.class);

    private static final String url = "jdbc:mysql://localhost/financial-accounting?serverTimezone=UTC";

    CreatingOperationService operationService;

    ClientService clientService;

    Long userId;

    public FinancialAccountingCommand(FinancialAccountingView view, CreatingOperationService operationService,
                                      ClientService clientService, Long userId) {
        this.view = view;
        this.operationService = operationService;
        this.clientService = clientService;
        this.userId = userId;
    }

    public void createOperation() {

        int type = view.operationMenu();
        Integer account = view.chooseAccount(clientService.findAccountsByUserId(userId));

        if (Objects.isNull(account)) {
            logger.warn("User with id {} hasn't accounts", account);
            return;
        }

        Long accountId = Long.parseLong(account.toString());
        Long transactionAmount = view.getTransactionAmount();

        if (type == 1) {
            Set<IncomeCategory> incomeCategorySet = view.chooseIncomeCategory(operationService.getAllIncomeCategories());
            try {
                operationService.createOperationIncome(new OperationSaveIncome(
                        accountId,
                        transactionAmount,
                        incomeCategorySet
                ));
                view.notificationAboutSuccessOperationCreated();
            } catch (FinancialAccountingExceptionDataNotFound e) {
                logger.warn("There is no account {}", accountId);
                System.out.println("There is no account with id " + accountId + " Please, choose an account again");
            }
            catch (IllegalArgumentException e) {
                view.warning();
            }
        }
        if (type == 2) {
            Set<ExpenseCategory> expenseCategories = view.chooseExpenseCategory(operationService.getAllExpenseCategories());
            try {
                operationService.createOperationExpense(new OperationSaveExpense(
                        accountId,
                        -transactionAmount,
                        expenseCategories
                ));
                view.notificationAboutSuccessOperationCreated();
            } catch (FinancialAccountingExceptionDataNotFound financialAccountingExceptionDataNotFound) {
                logger.warn("There is no account {}", accountId);
                System.out.println("There is no account with id " + accountId + " Please, choose an account again");
            } catch (IllegalArgumentException e) {
                view.warning();
            }
        }
    }

    public void makeQuittance(String name, String password) {
        Instant[] dates = view.getPeriod();
        Long accountId = Long.valueOf(view.chooseAccount(clientService.findAccountsByUserId(userId)));
        QuittanceService quittanceService = new JDBCFinancialService(url, name, password);
        List<OperationRecord> operations = quittanceService.getOperationsByTimePeriod(accountId, dates[0], dates[1]);
        QuittanceRecord quittance = quittanceService.generateQuittance(operations, quittanceService.generalIncome(operations), quittanceService.balanceInHand(operations));
        CSVParser.createQuittance(quittance);
    }

}
