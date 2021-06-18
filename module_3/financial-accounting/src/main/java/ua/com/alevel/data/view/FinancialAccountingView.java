package ua.com.alevel.data.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.data.controller.FinancialAccountingController;
import ua.com.alevel.data.model.dto.AccountRecord;
import ua.com.alevel.data.model.entity.*;
import ua.com.alevel.data.service.jpa.JPACreateOperation;
import ua.com.alevel.data.util.DateUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FinancialAccountingView {

    private static final Logger logger = LoggerFactory.getLogger(FinancialAccountingView.class);

    private static final int MIN_OPTION_MENU = 0;
    private static final int MAX_OPTION_MENU = 2;

    private static final int INCOME_OPERATION = 1;
    private static final int EXPENSE_OPERATION = 2;

    public void mainMenu() {
        System.out.println("Hello dear, user. Please enter data to work with database: ");
        System.out.print("Enter the username: ");
        String name = consoleReader();
        System.out.print("Enter the password: ");
        String password = consoleReader();

        FinancialAccountingController controller = new FinancialAccountingController();
        controller.run(name, password);

    }

    public int getUserId() {
        System.out.print("Please, input your id: ");
        return consoleReaderNumber(0, Integer.MAX_VALUE);
    }

    public int chooseOption() {
        System.out.print("\nPlease, choose an option: \nCreate an operation: 1\nView quittance: 2\nExit: 0\n");
        return consoleReaderNumber(MIN_OPTION_MENU, MAX_OPTION_MENU);
    }

    public int operationMenu() {
        System.out.println("Creating an operation\nPlease,choose type of operation:\n" +
                "Income: 1\nExpense: 2");
        return consoleReaderNumber(INCOME_OPERATION, EXPENSE_OPERATION);
    }

    public Integer chooseAccount(List<AccountRecord> accounts) {
        System.out.println("Please, choose the account that you want to use: ");
        if (accounts.isEmpty()) {
            System.out.println("You have no accounts");
            return null;
        }
        accounts.forEach(a -> System.out.println("Account " + a.id()));
        return consoleReaderNumber();
    }

    public Long getTransactionAmount() {
        System.out.print("Please, enter an transaction amount: ");
        return (long) (consoleReaderNumber());
    }

    public Set<IncomeCategory> chooseIncomeCategory(List<IncomeCategory> categories) {
        System.out.println("\nPlease, list the income category separated by comma\nAvailable types are: ");

        for (IncomeCategory category : categories) {
            System.out.println(category.getType().toString().toLowerCase());
        }
        System.out.print("type here: ");
        String[] separatedTypes = getCategories();
        return categories.stream()
                .filter(ic -> {
                    boolean flag = false;
                    for (String separatedType : separatedTypes) {
                        if (ic.getType().toString().toLowerCase().equals(separatedType)) {
                            flag = true;
                        }
                    }
                    return flag;
                })
                .collect(Collectors.toSet());

    }

    public Set<ExpenseCategory> chooseExpenseCategory(List<ExpenseCategory> categories) {
        System.out.println(" Please, list the types separated by comma\nAvailable types are: ");

        for (ExpenseCategory category : categories) {
            System.out.println(category.getType().toString().toLowerCase());
        }
        String[] separatedTypes = getCategories();
        return categories.stream()
                .filter(ic -> {
                    boolean flag = false;
                    for (String separatedType : separatedTypes) {
                        if (ic.getType().toString().toLowerCase().equals(separatedType)) {
                            flag = true;
                        }
                    }
                    return flag;
                })
                .collect(Collectors.toSet());
    }

    public void warning() {
        System.out.println("Please note, transaction amount must not be 0 or less then your balance");
    }

    public Instant[] getPeriod() {
        System.out.println("Please, input date from in format yyyy/mm/dd");
        String dateFrom = consoleReader();

        System.out.println("Please, input date to in format yyyy/mm/dd");
        String dateTo = consoleReader();


        if (DateUtil.checkDate(dateFrom) && DateUtil.checkDate(dateTo)) {
            Instant[] dates = new Instant[2];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate dateStart = LocalDate.parse(dateFrom, formatter);
            LocalDate dateEnd = LocalDate.parse(dateTo, formatter);
            dates[0] = dateStart.atStartOfDay().toInstant(ZoneOffset.UTC);
            dates[1] = dateEnd.atStartOfDay().toInstant(ZoneOffset.UTC);
            return dates;
        } else {
            System.out.println("Please input date in correct format");
            return getPeriod();
        }
    }

    private String[] getCategories() {
        String types = consoleReader();
        return types.trim().replace("\\s+", "").split(",");
    }

    public void notificationAboutSuccessOperationCreated() {
        System.out.println("Operation was successfully added");
    }

    private String consoleReader() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            logger.error("Problems were occurred during work with buffering read ");
            throw new RuntimeException(e);
        }
    }

    private int consoleReaderNumber(int min, int max) {
        String number = consoleReader();
        try {
            int option = Integer.parseInt(number);
            if (option >= min && option <= max)
                return option;
            else throw new NumberFormatException();
        } catch (NumberFormatException e) {
            logger.error("Incorrect number format were used");
            System.out.print("Please, input correct number: ");
            return consoleReaderNumber(min, max);
        }
    }

    private int consoleReaderNumber() {
        String number = consoleReader();
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            logger.error("Incorrect number format were used");
            System.out.println("Please, input correct number");
            return consoleReaderNumber();
        }
    }

}
