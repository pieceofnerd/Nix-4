package ua.com.alevel.data.service.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.data.exception.FinancialAccountingExceptionDataNotFound;
import ua.com.alevel.data.model.dto.AccountRecord;
import ua.com.alevel.data.model.dto.OperationSaveExpense;
import ua.com.alevel.data.model.dto.OperationSaveIncome;
import ua.com.alevel.data.model.entity.*;
import ua.com.alevel.data.service.CreatingOperationService;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;


public class JPACreateOperation
        implements CreatingOperationService {

    private static final Logger logger = LoggerFactory.getLogger(JPACreateOperation.class);

    private final Supplier<EntityManager> em;

    public JPACreateOperation(Supplier<EntityManager> em) {
        this.em = em;
    }

    @Override
    public void createOperationIncome(OperationSaveIncome operationSaveIncome) throws FinancialAccountingExceptionDataNotFound {
        EntityManager jpa = em.get();

        EntityTransaction transaction = jpa.getTransaction();
        transaction.begin();

        try {
            Optional<AccountRecord> clientAccount = findAccountById(operationSaveIncome.accountId());

            if (clientAccount.isPresent()) {
                AccountRecord record = clientAccount.get();
                Operation operation = new Operation(record.balance(), operationSaveIncome.transactionAmount(), mapRecordToEntity(record));
                for (IncomeCategory incomeCategory : operationSaveIncome.incomeCategories()) {
                    operation.setType(incomeCategory);
                }

                Long balanceAfter = makeFinancialTransaction(record.balance(), operationSaveIncome.transactionAmount());
                operation.setBalanceAfter(balanceAfter);
                mapRecordToEntity(clientAccount.get()).setBalance(balanceAfter);


                jpa.persist(operation);
                updateAccountBalance(jpa, clientAccount.get(), balanceAfter);
                logger.info("Operation was successfully added");
                transaction.commit();
            } else {
                transaction.rollback();
                String warningMassage = "Cannot find account with id {}";
                logger.warn(warningMassage, operationSaveIncome.accountId());
                throw new FinancialAccountingExceptionDataNotFound(warningMassage);
            }

        } catch (FinancialAccountingExceptionDataNotFound e) {
            transaction.rollback();
            String error = "Problem occurs during creating operation";
            logger.error(error);
            throw new FinancialAccountingExceptionDataNotFound(error, e);
        }

    }

    private void updateAccountBalance(EntityManager jpa, AccountRecord clientAccount, Long balanceAfter) {
        Account account= mapRecordToEntity(clientAccount);
        account.setBalance(balanceAfter);
        jpa.merge(account);
    }

    @Override
    public void createOperationExpense(OperationSaveExpense operationSaveExpense) throws FinancialAccountingExceptionDataNotFound {
        EntityManager jpa = em.get();

        EntityTransaction transaction = jpa.getTransaction();
        transaction.begin();

        try {
            Optional<AccountRecord> clientAccount = findAccountById(operationSaveExpense.accountId());

            if (clientAccount.isPresent()) {
                AccountRecord record = clientAccount.get();
                Operation operation = new Operation(record.balance(), operationSaveExpense.transactionAmount(), mapRecordToEntity(record));
                Long balanceAfter = makeFinancialTransaction(record.balance(), operationSaveExpense.transactionAmount());
                for (ExpenseCategory expenseCategory : operationSaveExpense.expenseCategories()) {
                    operation.setType(expenseCategory);
                }
                operation.setBalanceAfter(balanceAfter);
                mapRecordToEntity(clientAccount.get()).setBalance(balanceAfter);


                jpa.persist(operation);
                updateAccountBalance(jpa, clientAccount.get(), balanceAfter);
                logger.info("Operation was successfully added");
                transaction.commit();
            } else {
                transaction.rollback();
                String warningMassage = "Cannot find account with id {}";
                logger.warn(warningMassage, operationSaveExpense.accountId());
                throw new FinancialAccountingExceptionDataNotFound(warningMassage);
            }

        } catch (FinancialAccountingExceptionDataNotFound e) {
            transaction.rollback();
            String error = "Problem occurs during creating operation";
            logger.error(error);
            throw new FinancialAccountingExceptionDataNotFound(error, e);
        }
    }

    @Override
    public Optional<AccountRecord> findAccountById(Long id) {
        EntityManager jpa = em.get();

        Account entity = jpa.find(Account.class, id);

        return Optional.ofNullable(entity)
                .map(this::mapEntityToRecord);
    }

    @Override
    public List<IncomeCategory> getAllIncomeCategories() {
        EntityManager jpa = em.get();

        return jpa.createQuery("SELECT a FROM IncomeCategory a", IncomeCategory.class).getResultList();
    }

    @Override
    public List<ExpenseCategory> getAllExpenseCategories() {
        EntityManager jpa = em.get();

        return jpa.createQuery("SELECT a FROM ExpenseCategory a", ExpenseCategory.class).getResultList();
    }

    private AccountRecord mapEntityToRecord(Account entity) {
        return new AccountRecord(
                entity.getId(),
                entity.getBalance(),
                entity.getClient()
        );
    }

    private Account mapRecordToEntity(AccountRecord record) {
        return new Account(
                record.id(),
                record.balance(),
                record.client()
        );
    }

    private Long makeFinancialTransaction(Long currentBalance, Long transactionAmount) {
        if (transactionAmount > 0) {
            return changeBalance(currentBalance, transactionAmount);
        } else if (transactionAmount < 0) {
            if (Math.abs(transactionAmount) < currentBalance) {
                return changeBalance(currentBalance, transactionAmount);
            } else {
                String error = "Transaction amount cannot be bigger than current balance";
                logger.error(error);
                throw new IllegalArgumentException(error);
            }
        } else {
            String error = "Transaction amount cannot be zero";
            logger.error(error);
            throw new IllegalArgumentException(error);
        }

    }

    private Long changeBalance(Long currentBalance, Long transactionAmount) {
        try {
            return currentBalance + transactionAmount;
        } catch (Exception e) {
            logger.error("Error occurs during transaction");
            throw new RuntimeException(e);
        }
    }

}

